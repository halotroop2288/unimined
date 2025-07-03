package com.unimined.api.configuration.patcher

import com.unimined.api.ComponentContainer
import com.unimined.api.EnvironmentComponent

/**
 * # Patchers Configuration
 *
 * This describes how Unimined should patch game and library files,
 * and how project files should be configured, built and finalized.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
class PatchersConfiguration(
	/**
	 * A list of patcher configurations that describes the order in which patchers should be applied.
	 *
	 * Later patchers in the list will be applied to the output of the previous patcher.
	 *
	 * e.g.
	 *
	 * # Standard Fabric usage
	 *
	 * 1. Remapper
	 * 2. Fabric Loader
	 *
	 * # Standard Forge usage
	 *
	 * 1. Remapper
	 */
	val sources: Array<PatcherConfiguration> = arrayOf(),
	/**
	 * Applicable environments for this patchers configuration.
	 *
	 * Any environment that is not covered will not be patched by this configuration.
	 */
	vararg environments: String = arrayOf("CLIENT", "SERVER", "COMBINED")
) : ComponentContainer(
	key = "Patchers Configuration",
	*sources,
	*EnvironmentComponent.arrayOf(*environments)
)
