package com.unimined.api.provider.project

import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.project.UniminedConfigBuilder
import com.unimined.api.configuration.project.UniminedConfiguration

/**
 * A dummy implementation of the Unimined Configuration Provider.
 *
 * Usually, this would require a supplier like Gradle or Maven.
 * But everything in here should be treated as no-op so it doesn't require any.
 *
 * This is *ONLY* to be used for testing parts of the API without access to a real implementation.
 */
object DummyUniminedConfigProvider: UniminedConfigProvider("Dummy") {
	class Configuration(
		configurationName: String,
		game: GameConfiguration,
//		decompiler: DecompilerConfiguration,
//		cache: CacheConfiguration,
	): UniminedConfiguration(configurationName, game)

	class Builder(
		configurationName: String,
		game: GameConfiguration
	) : UniminedConfigBuilder<Configuration>(
		configurationName, game
	) {


		override fun build() = Configuration()
	}

	override fun builder(name: String): Builder {
		TODO("Not yet implemented")
	}
}