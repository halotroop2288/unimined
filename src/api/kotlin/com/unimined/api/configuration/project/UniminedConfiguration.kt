package com.unimined.api.configuration.project

import com.unimined.api.ComponentContainer
import com.unimined.api.configuration.game.GameConfiguration

/**
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class UniminedConfiguration(
	uniqueName: String,
	game: GameConfiguration,
//	decompiler: DecompilerConfiguration,
//	cache: CacheConfiguration,
): ComponentContainer(
	uniqueName,
	game,
	TODO("Decompiler settings"),
	TODO("Cache settings")
)

/**
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class UniminedConfigBuilder<out T : UniminedConfiguration>(
	/**
	 * This name can be anything, but it has to be unique among all the other Unimined configurations!
	 * It will uniquely identify the provider in lookups.
	 *
	 * **Note: Case-sensitive!**
	 *
	 * e.g
	 * - Single target: "Main"
	 * - Single version, multiple loader: "Forge, "Fabric",
	 * - Multi-version: "1.12.2", "1.13.2", "1.14.4"
	 * - Multi-version, multi-loader: "Fabric 1.13.2", "Rift 1.13.2", "Architectury 1.14.4"
	 *
	 * @since 2.0.0
	 */
	var configurationName: String = "Main",
	var gameConfiguration: GameConfiguration,
): () -> T {
	fun name(value: String): UniminedConfigBuilder<T> = apply { configurationName = value }
}
