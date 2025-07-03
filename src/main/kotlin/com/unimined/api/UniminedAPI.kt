package com.unimined.api

import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.patcher.PatcherConfiguration
import com.unimined.api.configuration.project.UniminedConfiguration
import com.unimined.api.provider.game.GameProvider
import com.unimined.api.provider.patcher.PatcherProvider
import com.unimined.api.provider.project.UniminedConfigProvider
import java.util.ServiceLoader
import kotlin.reflect.KClass

/**
 * # Documentation for the main API
 *
 * Don't use this class, it exists to describe how the API could be used on its own,
 * such as for testing purposes.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
interface UniminedAPI {
	val configProvider: UniminedConfigProvider
	val gameProviders: Set<GameProvider>
	val patcherProviders: Set<PatcherProvider>

	/**
	 * Resolve these configurations to start the process!
	 *
	 * @since 2.0.0
	 */
	val configurations: HashSet<() -> UniminedConfiguration>

	/**
	 * @return a Unimined configuration supplier.
	 */
	fun configureUnimined(
		configName: String = "Main",
		game: () -> GameConfiguration = configureGame(),
	//	decompiler: () -> DecompilerConfiguration = TODO(),
	//	cache: () -> () -> CacheConfiguration = TODO(),
	): () -> UniminedConfiguration

	/**
	 * @return a game configuration supplier.
	 */
	fun configureGame(
		gameName: String = "Unknown",
		patchers: Array<() -> PatcherConfiguration> = arrayOf(),
	): () -> GameConfiguration = fun() = gameProviders.singleKey(gameName)(
		patchers = patchers.map { it() }.toTypedArray()
	)
}

/**
 * Shorthand for lazy-loading Java services via
 * ```kt
 * val services: Set<Type> by lazy {
 *     ServiceLoader.load(Type::class.java).toSet()
 * }
 * ```
 */
class LazyServices<T : Any>(type: KClass<T>): Lazy<Set<T>> {
	var initialized: Boolean = false

	override val value: Set<T> by lazy {
		initialized = true
		ServiceLoader.load(type.java).toSet()
	}

	override fun isInitialized(): Boolean = initialized
}
