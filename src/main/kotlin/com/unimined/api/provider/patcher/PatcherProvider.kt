package com.unimined.api.provider.patcher

import com.unimined.api.Component
import com.unimined.api.UniminedAPI
import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.patcher.PatcherConfiguration

/**
 * # Patcher Provider
 *
 * Patcher providers must be invoked with [patcher configurations][PatcherConfiguration]
 * Many patchers may be applied to a [GameConfiguration].
 *
 * ```kt
 * val gameProvider: GameProvider = TODO("Your provider here. Example: Minecraft")
 * val patcherProvider: PatcherProvider = TODO("Your provider here. Example: Remapper")
 * val patcherConfiguration: PatcherConfiguration = provider(configuration)
 * val patchers = listOf(patcherConfiguration)
 * val gameConfiguration: GameConfiguration = gameProvider(patchers = patchers)
 * ```
 *
 * Those configurations will then be passed back into the provider's functions to apply them.
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
abstract class PatcherProvider(
	/**
	 * This name can be anything, but it has to be unique among all the other patcher providers!
	 *
	 * It will uniquely identify the provider in lookups from [UniminedAPI.patcherProviders].
	 *
	 * **Note: Case-sensitive!**
	 *
	 * e.g
	 * - `"Remapper"`
	 * - `"Forge Mod Loader"`, `"Fancy Mod Loader"`
	 * - `"Minecraft Forge"`, `"NeoForge"`, `"Cleanroom"`
	 * - `"Fabric Loader"`, `"Quilt Loader"`
	 * - `"CraftBukkit"`, `"Spigot"`, `"Paper"`
	 * - `"FoxLoader"`
	 *
	 * @since 2.0.0
	 */
	uniqueName: String,
): Component(uniqueName)
