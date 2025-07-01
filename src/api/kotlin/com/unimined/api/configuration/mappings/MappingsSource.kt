package com.unimined.api.configuration.mappings

import com.unimined.api.NameComponent

/**
 * # Obfuscation Mappings Source
 *
 * For lack of a better term, this class describes how to resolve the file containing the set of obfuscation mappings.
 */
abstract class MappingsSource(
	/**
	 * The name of the obfuscation mappings this source provides.
	 */
	name: String,

): NameComponent("Mappings Source", name)
