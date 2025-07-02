package com.unimined.api.provider.mappings

import com.unimined.api.ComponentContainer
import com.unimined.api.configuration.mappings.MappingsSource

/**
 * # Mappings Provider
 *
 * Handles setup of individual mappings sets.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class MappingsSourceProvider(
	/**
	 * This name can be anything, but it has to be unique among all the other mappings providers!
	 * It will uniquely identify the provider in lookups.
	 *
	 * **Note: Case-sensitive!**
	 *
	 * e.g
	 * `"Mojmap"`,
	 * `"NMS"`,
	 *
	 * `"Searge"`,
	 *
	 * `"AccessWidener'`, `""`,
	 * `"Ploceus"`, `"Babric Intermediary"`, `"Legacy Fabric Intermediary"`, `"Fabric Intermediary"`, `"Hashed Mojmap"`,
	 *
	 * `"RetroMCP"`, `"Unknown Thingy"`, `"MCP Legacy"`, `"MCP Snapshot"`, `"MCP Stable"`,
	 * `"Feather"`, `"Biny"`, `"Legacy Yarn"`, `"Yarn"`
	 *
	 * @since 2.0.0
 	 */
	sourceName: String,
	/**
	 * The format of the mappings file provided by this mappings source.
	 *
	 * - See [Unimined Mapping Library](https://github.com/Unimined/UniminedMappingLibrary)
	 *
	 * @since 2.0.0
	 */
	format: String,
) : ComponentContainer(
	key = "$sourceName Mappings Provider"
) {
	abstract operator fun invoke(): MappingsSource
}
