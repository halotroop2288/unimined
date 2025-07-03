package com.unimined.api.provider.mappings

import com.unimined.api.ComponentContainer
import com.unimined.mappings.configuration.patcher.remapper.MappingsSource

/**
 * # Mappings Source Provider
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
	 * e.g.
	 * - `"Mojmap"`, `"NMS"`,
	 * - `"AccessWidener'`, `"AccessTransformer"`,
	 * - `"Searge"`,
	 * - `"Ploceus"`, `"Babric Intermediary"`, `"Legacy Fabric Intermediary"`, `"Fabric Intermediary"`, `"Hashed Mojmap"`,
	 * - `"RetroMCP"`, `"Unknown Thingy"`, `"MCP Legacy"`, `"MCP Snapshot"`, `"MCP Stable"`,
	 * - `"Feather"`, `"Biny"`, `"Legacy Yarn"`, `"Yarn"`
	 *
	 * @since 2.0.0
 	 */
	sourceName: String,
	/**
	 * The format of the mappings file provided by this mappings source.
	 *
	 * e.g.
	 * - `"Enigma"`, `"ProGuard"`
	 * - `"SRG"`, `"CSRG"`, `"TSRG"`,
	 * - `"TinyV1"`, `"TinyV2"`,
	 * - `"AccessWidener"`, `"AccessTransformer"`
	 * - `"InterfaceInjector"`
	 *
	 * See [Unimined Mapping Library](https://github.com/Unimined/UniminedMappingLibrary)
	 * for a full list of supported formats.
	 *
	 * @since 2.0.0
	 */
	format: String,
) : ComponentContainer(
	key = "$sourceName Mappings Provider"
) {
	abstract operator fun invoke(): MappingsSource
}
