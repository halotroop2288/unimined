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
	/**
	 * The name of the game configuration that owns this configuration.
	 *
	 * This should be passed in by the game provider.
	 * It will be used to uniquely identify this configuration.
	 *
	 * @since 2.0.0
	 */
	gameConfigurationUniqueName: String,
	components: HashSet<Component>,
) : ComponentContainer(gameConfigurationUniqueName, components + NameComponent(gameConfigurationUniqueName)) {
}
