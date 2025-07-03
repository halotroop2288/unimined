package com.unimined.gradle.provider.project

import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.project.UniminedConfiguration
import com.unimined.api.provider.project.UniminedConfigProvider
import com.unimined.gradle.configuration.project.GradleUniminedConfiguration
import org.gradle.api.Project

/**
 * # Unimined Configuration Provider for Gradle Projects
 *
 * @author halotroop2288
 * @since 2.0.0
 */
class GradleUniminedConfigProvider(val project: Project) : UniminedConfigProvider("Gradle Project") {
	override fun invoke(
		configurationName: String,
		game: GameConfiguration,
	): UniminedConfiguration = GradleUniminedConfiguration(
		configName = configurationName,
		game = game,
		project = project
	)
}
