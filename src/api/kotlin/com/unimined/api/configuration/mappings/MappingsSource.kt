package com.unimined.api.configuration.mappings

import com.unimined.api.NameComponent

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
	 */
	name: String,
): NameComponent("Mappings Source", name) {
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
	protected abstract fun apply()
}
