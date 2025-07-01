package com.unimined.api.provider.patcher

import com.unimined.api.Component
import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.patcher.PatcherConfiguration

/**
 * Many patcher configurations may be applied to a [GameConfiguration].
 * Patcher Providers supply [PatcherConfiguration] instances.
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
	 * It will uniquely identify the provider in lookups.
	 *
	 * **Note: Case-sensitive!**
	 *
	 * e.g
	 * - `"Access Transformer"`, `"Access Widener"`
	 * - `"Forge Mod Loader"`, `"Fancy Mod Loader"`
	 * - `"Minecraft Forge"`, `"NeoForge"`
	 * - `"Fabric Loader"`, `"Quilt Loader"`
	 *
	 * @since 2.0.0
	 */
	uniqueName: String,
): Component(uniqueName), (String) -> PatcherConfiguration
