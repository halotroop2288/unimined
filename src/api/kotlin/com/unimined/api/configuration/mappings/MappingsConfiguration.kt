package com.unimined.api.configuration.mappings

import com.unimined.api.Component
import com.unimined.api.NameComponent
import com.unimined.api.ComponentContainer

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
	componentName: String = "Mappings Configuration",
	sources: List<MappingsSource> = TODO(),
) : ComponentContainer(componentName, setOf<Component>().apply {

} + NameComponent(componentName))
