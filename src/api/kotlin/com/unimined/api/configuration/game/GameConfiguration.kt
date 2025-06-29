package com.unimined.api.configuration.game

import com.unimined.api.Builder
import com.unimined.api.Component
import com.unimined.api.ComponentContainer
import com.unimined.api.configuration.mappings.MappingsConfiguration

/**
 * # Game Provider Configuration
 *
 * This describes how Unimined should set up a game for the project.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
open class GameConfiguration internal constructor(
	gameName: String,
	mappings: MappingsConfiguration? = null,
//	patchers: PatchersConfiguration? = null
) : ComponentContainer(gameName, hashSetOf<Component>().apply {
	mappings?.let { add(it) }
	TODO("patchers?.let { add(it) }")
})

/**
 * # Game Provider Configuration Builder
 *
 * @author halotroop2288
 * @since 2.0.0
 */
open class GameBuilder<T : GameConfiguration>(
	/**
	 * A unique identifier for the configuration which will be prefixed on source sets, etc.
	 *
	 * This is usually set by the game provider itself.
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
	var gameName: String,

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
	var gameVersion: String? = null,
) : Builder<GameConfiguration> {
	override fun build(): GameConfiguration = GameConfiguration(gameName)

	/**
	 * @since 2.0.0
	 * @see [gameName]
	 */
	fun name(value: String): GameBuilder<T> = apply { gameName = value }

	/**
	 * @since 2.0.0
	 * @see [gameVersion]
	 */
	fun version(value: String): GameBuilder<T> = apply { gameVersion = value }
}
