import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.io.ByteArrayOutputStream
import java.io.File

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dokka)
    `java-gradle-plugin`
    `maven-publish`
}

group = project.properties["maven_group"].toString()
base.archivesName = project.properties["archives_base_name"].toString()
version = project.properties["version"].toString() + if (project.hasProperty("version_snapshot")) "-SNAPSHOT" else ""

val javaVersion = libs.versions.java.get().toInt()

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
    withSourcesJar()
}

kotlin.jvmToolchain(javaVersion)

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.wagyourtail.xyz/releases")
    maven("https://maven.wagyourtail.xyz/snapshots")
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.minecraftforge.net")
    maven("https://maven.fabricmc.net/")
    gradlePluginPortal()
}

sourceSets {
    fun SourceSet.inputOf(vararg sourceSets: SourceSet) = sourceSets.forEach {
		compileClasspath += it.compileClasspath
		runtimeClasspath += it.runtimeClasspath
	}

    fun SourceSet.outputOf(vararg sourceSets: SourceSet) = sourceSets.forEach {
		compileClasspath += it.output
		runtimeClasspath += it.output
	}

    // Please don't add plugins or their dependencies to the API or implementation's classpath.
    // It will inevitably cause the code to unravel.
    val main: SourceSet by getting

    val gradle: SourceSet by creating {
        inputOf(main)
        outputOf(main)
    }

    // Remapper Patcher Plugin
    val remapperPlugin: SourceSet by creating {
        inputOf(main)
        outputOf(main)
    }

    val minecraftPlugin: SourceSet by creating {
        inputOf(main, remapperPlugin)
        outputOf(main, remapperPlugin)
    }

    val fabricmcPlugin: SourceSet by creating {
        inputOf(main, remapperPlugin)
        outputOf(main, remapperPlugin)
    }

    val quiltmcPlugin: SourceSet by creating {
        inputOf(main, remapperPlugin, fabricmcPlugin)
        outputOf(main, remapperPlugin, fabricmcPlugin)
    }

    val lexforgePlugin: SourceSet by creating {
        inputOf(main, minecraftPlugin)
        outputOf(main, minecraftPlugin)
    }

    val neoforgedPlugin: SourceSet by creating {
        inputOf(main, lexforgePlugin, minecraftPlugin)
        outputOf(main, lexforgePlugin, minecraftPlugin)
    }

    val test: SourceSet by getting {
        val sets = sourceSets.filterNot { it == this }.toTypedArray()
        inputOf(*sets)
        outputOf(*sets)
    }
}

dependencies {
    implementation(kotlin("metadata-jvm"))
    implementation(libs.jb.annotations)
    implementation(libs.wagyourtail.commons)

    "gradleRuntimeOnly"(gradleApi())

    "gradleImplementation"(libs.guava)
    "gradleImplementation"(libs.gson)

    "gradleImplementation"(libs.asm)
    "gradleImplementation"(libs.asm.commons)
    "gradleImplementation"(libs.asm.tree)
    "gradleImplementation"(libs.asm.analysis)
    "gradleImplementation"(libs.asm.util)

    "remapperPluginImplementation"(libs.unimined.mapping.library.jvm)
    "remapperPluginImplementation"(libs.tiny.remapper) {
        exclude(group = "org.ow2.asm")
    }

    implementation(libs.binarypatcher) {
        exclude(mapOf("group" to "commons-io"))
    }

    implementation(libs.access.widener)

    implementation(libs.commons.io)
    implementation(libs.commons.compress)

    implementation(libs.jgit)

    implementation(libs.java.keyring)
    implementation(libs.minecraftauth)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks {
    wrapper {
        gradleVersion = libs.versions.gradle.get()
    }

    jar {
        enabled = false
    }

    val implementationVersion: Pair<String, String> by lazy {
        "Implementation-Version" to if (project.hasProperty("version_snapshot")) {
            val stdout = ByteArrayOutputStream()
            @Suppress("DEPRECATION")
            project.exec {
                commandLine("git", "rev-parse", "--short", "HEAD")
                standardOutput = stdout
            }.assertNormalExitValue()
            buildString {
                append(project.version.toString().removeSuffix("-SNAPSHOT"))
                append("-")
                append(stdout.toString().trim())
                append("-SNAPSHOT")
            }
        } else project.version.toString()
    }

    val apiJar by registering(Jar::class) {
        group = "build"
        archiveBaseName = "api"
        from(sourceSets["main"].output)

        manifest {
            attributes(implementationVersion)
        }
    }

    val apiSourcesJar by registering(Jar::class) {
        group = "build"
        archiveBaseName = "api"
        archiveClassifier = "sources"
        from(sourceSets["gradle"].allSource)

        manifest {
            attributes(implementationVersion)
        }
    }

    // Built-in plugin implementations, included in all implementations
    val patcherSets by lazy {
        sourceSets.filter { it.name.endsWith("Plugin") }
    }

    val gradleJar by registering(Jar::class) {
        group = "build"
        archiveBaseName = "gradle"
        from(
            sourceSets["main"].output,
            sourceSets["gradle"].output,
            patcherSets.map { it.output }
        )

        manifest {
            attributes(implementationVersion)
        }
    }

    val gradleSourcesJar by registering(Jar::class) {
        group = "build"
        archiveBaseName = "gradle"
        archiveClassifier = "sources"
        from(
            sourceSets["main"].allSource,
            sourceSets["gradle"].allSource,
            patcherSets.map { it.allSource }
        )

        manifest {
            attributes(implementationVersion)
        }
    }

    build {
        dependsOn(apiJar, gradleSourcesJar)
    }

    test {
        useJUnitPlatform()

        testLogging {
            events.add(TestLogEvent.PASSED)
            events.add(TestLogEvent.SKIPPED)
            events.add(TestLogEvent.FAILED)
        }
    }

    dokkaHtml {
        outputDirectory.set(projectDir.resolve("docs/api-docs/"))
        dokkaSourceSets {
            named("main") {
                suppress = false
            }
            named("gradle") {
                suppress = true
            }
        }
        doFirst {
            file("Writerside/v.list").writeText(
                """
                <?xml version="1.0" encoding="UTF-8"?>
                <!DOCTYPE vars SYSTEM "https://resources.jetbrains.com/writerside/1.0/vars.dtd">
                <vars>
                    <var name="version" value="${project.version}"/>
                </vars>
            """.trimIndent()
            )
        }
    }
}

gradlePlugin {
    plugins {
        create("unimined") {
            id = "$group"
            implementationClass = "$group.gradle.UniminedGradlePlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "WagYourMaven"
            url = uri("https://maven.wagyourtail.xyz/" + if (project.hasProperty("version_snapshot")) "snapshots/" else "releases/")
            credentials {
                username = project.findProperty("mvn.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("mvn.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}

// A task to output a json file with a list of all the test to run
val writeActionsTestMatrix by tasks.registering {
    doLast {
        val testMatrix = arrayListOf<String>()

        file("src/test/kotlin/com/unimined/test/integration").listFiles()?.forEach {
            if (it.name.endsWith("Test.kt")) {
                val className = it.name.replace(".kt", "")
                testMatrix.add("$group.test.integration.${className}")
            }
        }

        testMatrix.add("$group.util.*")

        val json = groovy.json.JsonOutput.toJson(testMatrix)
        val output = file("build/test_matrix.json")
        output.parentFile.mkdir()
        output.writeText(json)
    }
}

/**
 * Replaces invalid characters in test names for GitHub Actions artifacts.
 */
abstract class PrintActionsTestName : DefaultTask() {
    @get:Input
    @get:Option(option = "name", description = "The test name")
    abstract val testName: Property<String>

    @TaskAction
    fun run() {
        val sanitised = testName.get().replace('*', '_')
        File(System.getenv()["GITHUB_OUTPUT"] ?: TODO()).writeText("\ntest=$sanitised")
    }
}

tasks.register<PrintActionsTestName>("printActionsTestName")
