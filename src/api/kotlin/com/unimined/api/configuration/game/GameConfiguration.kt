package com.unimined.api.configuration.game

import com.unimined.api.Component
import com.unimined.api.ComponentContainer
import com.unimined.api.configuration.mappings.MappingsConfiguration
import com.unimined.api.configuration.patcher.PatcherConfiguration

/**
 * # Game Configuration
 *
 * This describes how Unimined should acquire and set up game files,
 * remap the obfuscated game files (if necessary),
 * patch the game files (if necessary),
 * configure the debugger for the game.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
open class GameConfiguration internal constructor(
	/**
	 * A unique identifier for the configuration which will be prefixed on source sets, etc.
	 *
	 * This needs to be set by the game provider itself.
	 *
	 * e.g.
	 * - "Minecraft"
	 * - "ReIndev"
	 * - "Better Than Adventure"
	 * - "Not So Seecret Saturday"
	 * - "RuneScape"
	 *
	 * @since 2.0.0
	 */
	gameName: String,

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

	mappings: List<MappingsConfiguration>,
	patchers: List<PatcherConfiguration>,
	environments: Set<String> = setOf("COMBINED")
) : ComponentContainer(gameName, hashSetOf<Component>().apply {
	addAll(mappings)
	addAll(patchers)
	environments.map {  }
})
