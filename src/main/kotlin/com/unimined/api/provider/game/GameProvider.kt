package com.unimined.api.provider.game

import com.unimined.api.Component
import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.patcher.PatcherConfiguration

/**
 * Exactly one game configuration may be registered per Unimined configuration.
 * Game Providers handle GameConfiguration instances.
 *
 * It should contain all the necessary information to create a mod or plugin
 * for exactly one version of the game,
 * with as many patchers as you need.
 *
 * If the game is obfuscated, mapping information should be provided.
 * If the mod requires a pre-patched version of the game, the patcher configuration should be provided.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class GameProvider(
	/**
	 * This name can be anything, but it has to be unique among all the other game providers!
	 * It will uniquely identify the provider in lookups.
	 *
	 * **Note: Case-sensitive!**
	 *
	 * e.g
	 * - `"Minecraft"`
	 * - `"ReIndev"`
	 * - `"Better Than Adventure"`
	 * - `"Not So Seecret Saturday"`
	 * - `"RuneScape"`
	 *
	 * @since 2.0.0
	 */
	uniqueName: String,
): Component(uniqueName) {
	abstract operator fun invoke(patchers: Array<PatcherConfiguration>): GameConfiguration
}
