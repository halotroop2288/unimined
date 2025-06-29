package com.unimined.gradle

import com.unimined.api.config
import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.UniminedProjectConfiguration as ProjectConfig
import org.gradle.api.Project as GradleProject

/**
 * @since 1.0.0
 */
val GradleProject.unimined: UniminedGradleExtension
	get() = extensions.getByType(UniminedGradleExtension::class.java)

/**
 * @since 1.0.0
 */
val GradleProject.uniminedMaybe: UniminedGradleExtension?
	get() = extensions.findByType(UniminedGradleExtension::class.java)

/**
 * The main Gradle project extension.
 *
 * @since 2.0.0
 */
abstract class UniminedGradleExtension(val project: GradleProject) {
	val configurations = hashSetOf<ProjectConfig>()

	/**
	 * Registers a Unimined project configuration for the Gradle project.
	 * The configuration will be used to generate source sets, download artifacts, process files, and
	 *
	 * @since 2.0.0
	 */
	fun configure(game: GameConfiguration): ProjectConfig {
		// TODO: Make a varargs version
		return config(project.name, game)
	}
}
