package com.unimined.api.configuration.game

import com.unimined.api.ComponentContainer
import com.unimined.api.EnvironmentComponent
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

	mappings: Array<MappingsConfiguration>,
	patchers: Array<PatcherConfiguration> = arrayOf(),
	vararg environments: String = arrayOf("COMBINED")
) : ComponentContainer(
	key = "$gameName Configuration",
	*mappings, *patchers,
	*EnvironmentComponent.arrayOf(*environments)
)
