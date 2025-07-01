package com.unimined.gradle

import com.unimined.api.UniminedAPI
import com.unimined.api.configuration.game.GameConfiguration
import com.unimined.api.configuration.mappings.MappingsConfiguration
import com.unimined.api.configuration.patcher.PatcherConfiguration
import com.unimined.api.configuration.project.UniminedConfiguration
import org.gradle.api.Project as GradleProject

/**
 * Returns the project's Unimined extension.
 *
 * @throws org.gradle.api.UnknownDomainObjectException
 * if the Unimined extension has not been registered yet.
 *
 * @since 2.0.0
 */
val GradleProject.unimined: UniminedGradleExtension
	get() = extensions.getByType(UniminedGradleExtension::class.java)

/**
 * Returns the project's unimined extension only if it has already been registered.
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
	override val configurations: HashSet<() -> UniminedConfiguration> = hashSetOf()

	override fun configureGame(
		gameName: String,
		mappings: () -> MappingsConfiguration,
		patchers: () -> PatcherConfiguration,
	): () -> GameConfiguration = fun() = UniminedAPI.GameProviders.find(gameName)(arrayOf(mappings()), arrayOf(patchers()))

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
