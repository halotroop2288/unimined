package com.unimined.api.configuration.patcher

import com.unimined.api.ComponentContainer

/**
 * # Patcher Configuration
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class PatcherConfiguration(
	/**
	 * A unique identifier for the configuration.
	 *
	 * This needs to be set by the patcher provider itself.
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
	patcherName: String,

	/**
	 * Optional. A valid and existing version name that corresponds to an existing released version of the game.
	 *
	 * e.g
	 * - "1.2.5"
	 * - "b1.7.3"
	 * - "a1.2.3"
	 * - "12w34a"
	 * - "20w14infinite"
	 *
	 * @since 2.0.0
	 */
	gameVersion: String? = null,
): ComponentContainer(patcherName, setOf(

))
