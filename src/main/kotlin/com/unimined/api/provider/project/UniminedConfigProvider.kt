package com.unimined.api.provider.project

import com.unimined.api.Component
import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.project.UniminedConfiguration

/**
 * # Unimined Configuration Provider
 *
 * Holds options for a single configuration of Unimined.
 *
 * It contains all the necessary information to create a mod or plugin,
 * including run options,
 *
 * If the game is obfuscated, mapping information should be provided.
 * If the mod requires a pre-patched version of the game, the patcher configuration should be provided.
 *
 * Many configurations may be registered per project,
 * the variety depends on the user's needs.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class UniminedConfigProvider(
	/**
	 * This name can be anything, but it has to be unique among all the other Unimined configuration providers!
	 * It will uniquely identify the provider in lookups.
	 *
	 * **Note: Case-sensitive!**
	 *
	 * e.g
	 * - "Gradle"
	 * - "Maven"
	 * - "IntelliJ"
	 *
	 * @since 2.0.0
	 */
	uniqueName: String,
): Component(uniqueName) {
	abstract operator fun invoke(configName: String = "Main", game: GameConfiguration): UniminedConfiguration
}
