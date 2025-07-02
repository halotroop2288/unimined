package com.unimined.api.configuration.mappings

import com.unimined.api.ComponentContainer
import com.unimined.api.LocalFileSourceComponent
import com.unimined.api.fileSourceComponent

/**
 * # Obfuscation Mappings Source
 *
 * For lack of a better term, this class describes how to resolve the file containing the set of obfuscation mappings.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class MappingsSource(
	/**
	 * The name of the obfuscation mappings this source provides.
	 *
	 * e.g
	 * `"Mojmap"`,
	 * `"NMS"`,
	 *
	 * `"Searge"`,
	 * `"Ploceus"`, `"Babric Intermediary"`, `"Legacy Fabric Intermediary"`, `"Fabric Intermediary"`, `"Hashed Mojmap"`,
	 *
	 * `"RetroMCP"`, `"Unknown Thingy"`, `"MCP Legacy"`, `"MCP Snapshot"`, `"MCP Stable"`,
	 * `"Feather"`, `"Biny"`, `"Legacy Yarn"`, `"Yarn"`
	 */
	name: String,

	val location: String
): ComponentContainer("Mappings Source", location.fileSourceComponent()) {
	/**
	 *
	 *
	 * e.g.
	 * - `null` before `Searge` or `Ploceus` or `Legacy Fabric Intermediary` or `Fabric Intermediary` or `Hashed Mojmap`
	 * (any Fabric-style intermediary)
	 * - `null` before `Mojmap` or `"NMS"`
	 * - `null` before `RetroMCP` or `MCP Legacy` (pre-Searge)
	 * - `Searge` before `Unknown Thingy`, `MCP Snapshot` or `MCP Stable` or `Forge Built-in MCP`
	 * - `Babric Intermediary` before `Biny`
	 * - `Ploceus` before `Feather`
	 * - `Legacy Intermediary` before `Legacy Yarn`
	 * - `Fabric Intermediary` before `Yarn`
	 *
	 *  @since 2.0.0
	 */
	protected abstract fun validatePosition(): Int
}
