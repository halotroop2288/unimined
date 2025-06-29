package com.unimined.api.provider.game

import com.unimined.api.Component
import com.unimined.api.configuration.game.GameBuilder
import com.unimined.api.configuration.game.GameConfiguration

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
open class GameProvider(
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
	/**
	 * # Generic Game Configuration Builder
	 *
	 * Used for games that don't have specific official support.
	 * You'll have to provide your own game files and will be limited to very few patchers.
	 *
	 * # Valid Uses
	 *
	 * ## Standard
	 * ```kt
	 * // Standard
	 *
	 * // You can explicitly set the configuration name,
	 * // which will be prefixed to the associated source sets, etc.
	 * val gameConfig = game.builder("The Game").build()
	 *
	 * // It defaults to "Main", which results in no additional source set prefix.
	 * val gameConfig = game.builder().build()
	 * ```
	 * ## Reuse
	 *
	 * ```kt
	 * // Create builder
	 * val gameBuilder = game.builder()
	 * // Late init name
	 * val aGameConfig = gameBuilder.name("Game A").build()
	 * // Reuse builder
	 * val bGameConfig = gameBuilder.name("Game B").build()
	 * ```
	 *
	 * This can be useful in multi-version or multi-game projects.
	 *
	 * ## Unnecessary
	 *
	 * ```kts
	 * import com.unimined.api.provider.game.GenericGameProvider
	 *
	 * val gameConfig = GenericGameProvider.Builder("A Game").build()
	 * ```
	 *
	 * This is possible but longer for no reason. Just use the builder function.
	 *
	 * @since 2.0.0
	 */
	open fun <T : GameConfiguration> builder(gameName: String = key): GameBuilder<T> =
		GameBuilder(gameName)
}
