import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.io.ByteArrayOutputStream
import java.io.File

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dokka)
    `java-gradle-plugin`
    `maven-publish`
}

version = project.properties["version"].toString() + if (project.hasProperty("version_snapshot")) "-SNAPSHOT" else ""
group = project.properties["maven_group"].toString()

val javaVersion = libs.versions.java.get().toInt()

base.archivesName = project.properties["archives_base_name"].toString()

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
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.fabricmc.net/")
    gradlePluginPortal()
}

sourceSets {
    fun SourceSet.inputOf(vararg sourceSets: SourceSet) {
        for (sourceSet in sourceSets) {
            compileClasspath += sourceSet.compileClasspath
            runtimeClasspath += sourceSet.runtimeClasspath
        }
    }

    fun SourceSet.outputOf(vararg sourceSets: SourceSet) {
        for (sourceSet in sourceSets) {
            compileClasspath += sourceSet.output
            runtimeClasspath += sourceSet.output
        }
    }

    val api: SourceSet by creating {
        inputOf(sourceSets.main.get())
    }

    val main: SourceSet by getting {
        outputOf(api)
    }

    val test: SourceSet by getting {
        inputOf(main)
        outputOf(api)
    }
}

dependencies {
//    runtimeOnly(gradleApi())
    implementation(kotlin("metadata-jvm"))
    implementation(libs.jb.annotations)

    implementation(libs.guava)
    implementation(libs.gson)

    implementation(libs.asm)
    implementation(libs.asm.commons)
    implementation(libs.asm.tree)
    implementation(libs.asm.analysis)
    implementation(libs.asm.util)

    implementation(libs.unimined.mapping.library.jvm)
    implementation(libs.tiny.remapper) {
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
        gradleVersion = project.properties["gradleVersion"].toString()
    }

    jar {
        from(
            sourceSets["api"].output,
            sourceSets["main"].output
        )

        manifest {
            attributes(
                "Implementation-Version" to if (project.hasProperty("version_snapshot")) {
                    val stdout = ByteArrayOutputStream()
                    exec {
                        commandLine("git", "rev-parse", "--short", "HEAD")
                        standardOutput = stdout
                    }.assertNormalExitValue()
                    buildString {
                        append(project.version.toString().removeSuffix("-SNAPSHOT"))
                        append("-")
                        append(stdout.toString().trim())
                        append("-SNAPSHOT")
                    }
                } else project.version
            )
        }
    }

    val sourcesJar by getting(Jar::class) {
        from(
            sourceSets["api"].allSource,
            sourceSets["main"].allSource
        )
    }

    val apiJar by registering(Jar::class) {
        from(sourceSets["api"].output)
    }

    val apiSourcesJar by registering(Jar::class) {
        from(sourceSets["api"].allSource)
    }

    build {
        dependsOn(sourcesJar)
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
                suppress = true
            }
            named("api") {
                suppress = false
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
        create("simplePlugin") {
            id = group.toString()
            implementationClass = "com.unimined.UniminedPlugin"
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
                testMatrix.add("com.unimined.test.integration.${className}")
            }
        }

        testMatrix.add("com.unimined.util.*")

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
    abstract val testName: Property<String>;

    @TaskAction
    fun run() {
        val sanitised = testName.get().replace('*', '_')
        File(System.getenv()["GITHUB_OUTPUT"] ?: TODO()).writeText("\ntest=$sanitised")
    }
}

tasks.register<PrintActionsTestName>("printActionsTestName") {}
