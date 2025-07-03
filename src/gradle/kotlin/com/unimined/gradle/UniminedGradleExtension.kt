package com.unimined.gradle

import com.unimined.api.LazyServices
import com.unimined.api.UniminedAPI
import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.patcher.PatcherConfiguration
import com.unimined.api.configuration.project.UniminedConfiguration
import com.unimined.api.provider.game.GameProvider
import com.unimined.api.provider.patcher.PatcherProvider
import com.unimined.api.provider.project.UniminedConfigProvider
import com.unimined.gradle.provider.project.GradleUniminedConfigProvider
import java.util.*
import org.gradle.api.Project as GradleProject

/**
 * The project's Unimined extension.
 *
 * @throws org.gradle.api.UnknownDomainObjectException if the Unimined extension has not been registered yet.
 *
 * @since 2.0.0
 */
val GradleProject.unimined: UniminedGradleExtension
	get() = extensions.getByType(UniminedGradleExtension::class.java)

/**
 * The project's unimined extension only if it has already been registered.
 *
 * @since 2.0.0
 */
val GradleProject.uniminedMaybe: UniminedGradleExtension?
	get() = extensions.findByType(UniminedGradleExtension::class.java)

/**
 * The main Gradle project extension.
 *
 * @since 2.0.0
 */
class UniminedGradleExtension(val project: GradleProject): UniminedAPI {
	override val configProvider: UniminedConfigProvider = GradleUniminedConfigProvider(project)
	override val gameProviders: Set<GameProvider> by LazyServices(GameProvider::class)
	override val patcherProviders: Set<PatcherProvider> by LazyServices(PatcherProvider::class)

	override val configurations: HashSet<() -> UniminedConfiguration> = hashSetOf()

	override fun configureUnimined(
		configName: String,
		game: () -> GameConfiguration,
	): () -> UniminedConfiguration = TODO("Not yet implemented")

	override fun configureGame(
		gameName: String,
		patchers: Array<() -> PatcherConfiguration>
	): () -> GameConfiguration {
		return super.configureGame(gameName, patchers)
	}

	/**
	 * Registers a Unimined project configuration for the Gradle project.
	 * The configuration will be used to generate source sets, download artifacts, process files, and
	 *
	 * @since 2.0.0
	 */
	fun registerConfiguration(configuration: () -> UniminedConfiguration) {
		configurations += configuration
	}
}
