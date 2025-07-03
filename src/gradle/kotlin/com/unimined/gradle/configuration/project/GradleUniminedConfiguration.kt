package com.unimined.gradle.configuration.project

import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.project.UniminedConfiguration
import org.gradle.api.Project

/**
 * # Unimined Configuration for Gradle Projects
 *
 * @author halotroop2288
 * @since 2.0.0
 */
class GradleUniminedConfiguration(
	configName: String = "Main",
	game: GameConfiguration,
	val project: Project,
) : UniminedConfiguration(configName, game)
