package com.unimined.api.configuration.mappings

import com.unimined.api.ComponentContainer
import com.unimined.api.EnvironmentComponent

/**
 * # Mappings Set Provider Configuration
 *
 * This describes how Unimined should resolve and apply (de-)obfuscation mappings to game files.
 * It should contain multiple
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class MappingsConfiguration(
	sources: Array<MappingsSource> = arrayOf(),
	vararg environments: String = arrayOf("COMBINED")
) : ComponentContainer(
	key = "Mappings Configuration",
	*sources,
	*environments.map { EnvironmentComponent(it) }.toTypedArray()
)
