package com.unimined.api.provider.mappings

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
	 * `"Ploceus"`, `"Babric Intermediary"`, `"Legacy Fabric Intermediary"`, `"Fabric Intermediary"`, `"Hashed Mojmap"`,
	 *
	 * `"RetroMCP"`, `"Unknown Thingy"`, `"MCP Legacy"`, `"MCP Snapshot"`, `"MCP Stable"`,
	 * `"Feather"`, `"Biny"`, `"Legacy Yarn"`, `"Yarn"`
	 *
	 * @since 2.0.0
	 */
	val uniqueName: String,

	/**
	 * The mappings set that needs to be applied before this one.
	 * Unimined will ensure the other one has been applied before it tries to apply this,
	 * without forcing it to be re-applied if it already has.
	 *
	 * `null` stands for the unmodified game files, aka *"Official"* (not to be confused with *"Mojmap"*),
	 * meaning no other mappings set needs to be applied before this one.
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
	 * @since 2.0.0
	 */
	val previous: MappingsSourceProvider? = null,
) : () -> MappingsSource
