package com.unimined.api

import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.mappings.MappingsConfiguration
import com.unimined.api.configuration.mappings.MappingsSource
import com.unimined.api.configuration.project.UniminedConfiguration
import com.unimined.api.provider.game.GameProvider
import com.unimined.api.provider.mappings.MappingsSourceProvider
import com.unimined.api.provider.project.UniminedConfigProvider
import java.util.ServiceLoader

/**
 * # Documentation for the main API
 *
 * Don't use this class, it exists to describe how the API could be used on its own,
 * such as for testing purposes.
 *
 * See `com.unimined.gradle.UniminedGradleExtension.kt#unimined` for Gradle usage.
 * See `com.unimined.maven.UniminedMavenPlugin` for Maven usage.
 *
 * # Developer usage:
 *
 * ```kt
 * import com.unimined.api.provider.game.game
 * import com.unimined.api.provider.game.
 *
 * // Creates a game configuration for Minecraft
 * val gameBuilder = minecraft(
 *     // Configures the game provider to download Minecraft version 1.2.5
 *     gameVersion = "1.2.5",
 *
 *     // Creates a mapping configuration with retroMCP layered on feather on ploceus intermediary.
 *     // Configures the remapper to build your final jars for both Ploceus-mapped and obfuscated games environments.
 *     mappings = listOf(
 *         mappings(
 *             sources = listOf(
 *                 ploceus(),
 *                 feather(12),
 *                 retroMCP(),
 *             ),
 *             finalizeTo = setOf("Ploceus", "Official"),
 *             environment = "COMBINED",
 *         ),
 *     ),
 *
 *     // Creates a patcher configuration that will install Fabric as the mod loader (launching through Knot)
 *     // and apply your access wideners for each source set.
 *     patchers = listOf(
 *         accessWidener("resources/modid.accesswidener"),
 *         fabricLoader("0.123.45"),
 *     )
 *
 *     // Creates source sets for client and server separately.
 *     environments = listOf("CLIENT", "SERVER"),
 * )
 *
 * // Finalizes the builder into an immutable configuration
 * val fabricMinecraftRelease125 = gameBuilder.build()
 * // Reconfigures Unimined to use the new configuration.
 * configure(fabricMinecraftRelease125)
 *
 * // You could stop here, or if you want to create a multi-loader project, you can reused the builder.
 *
 * // Creates a new patcher configuration that will install ForgeModLoader and Minecraft Forge.
 * // The latest versions will be pulled from an online metadata source and assigned automatically.
 * gameBuilder.patchers(patchers().forgeModLoader().minecraftForge().build())
 *
 * // Finalizes the builder into a new, separate immutable configuration
 * val forgeMinecraftRelease125 = gameBuilder.build()
 *
 * // Reconfigures Unimined to use both configurations.
 * configure(
 *     projectName = "Project-Name-Here",
 *     fabricMinecraftRelease125, forgeMinecraftRelease125
 * )
 * ```
 *
 * @author halotroop2288
 * @since 2.0.0
 */
object UniminedAPI {
	val configProvider: UniminedConfigProvider by lazy {
		ServiceLoader.load(UniminedConfigProvider::class.java).single()
	}

	val gameProviders: List<GameProvider> by lazy {
		ServiceLoader.load(GameProvider::class.java).toList()
	}

	val mappingsSourceProviders: List<MappingsSourceProvider> by lazy {
		ServiceLoader.load(MappingsSourceProvider::class.java).toList()
	}
}

val configuration = configurationBuilder()

fun configurationBuilder(
	configName: String = "Main",
	game: () -> GameConfiguration = gameBuilder(),
//	decompiler: () -> Any = TODO(),
//	cache: () -> () -> Any = TODO(),
): () -> UniminedConfiguration = fun() = UniminedAPI.configProvider(configName, game())

fun gameBuilder(
	gameName: String = "Minecraft",
	mappings: () -> List<MappingsConfiguration> = listOf(mappingsConfigurationBuilder()),
	patchers: () -> Any = TODO(),
): () -> GameConfiguration = fun() = UniminedAPI.gameProviders.single {
	it.key == gameName
}(mappings(), patchers())

fun mappingsConfigurationBuilder(
	sources: () -> List<() -> MappingsSource> = TODO(),
	environment: () -> String = fun(): String = "COMBINED",
): () -> MappingsConfiguration

fun mappingsSourceBuilder(
	sourceName: String
) = fun() = UniminedAPI.mappingsSourceProviders.single {
	it.key == sourceName
}
