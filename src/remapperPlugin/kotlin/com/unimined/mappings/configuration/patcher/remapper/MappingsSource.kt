package com.unimined.mappings.configuration.patcher.remapper

import com.unimined.api.ComponentContainer
import com.unimined.api.configuration.ListRectifier
import com.unimined.api.fileSourceComponent

/**
 * # Obfuscation Mappings Source
 *
 * This class describes how to resolve the file containing the set of obfuscation mappings.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
open class MappingsSource(
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
): ComponentContainer("Mappings Source", location.fileSourceComponent()), ListRectifier<MappingsSource> {
	/**
	 * Reads the list of mappings sources and validates its position within.
	 *
	 * The returned list may have more patchers added if needed, but none should be removed.
	 *
	 * This should throw an error if the position can't be rectified.
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
	override fun rectifyList(
		/**
		 * The position of this source in the list of mappings.
		 *
		 * When this patcher is applied, the mappings  will already have been applied.
		 *
		 * e.g. `2` if this is the third source (arrays are zero-indexed)
		 */
		position: Int,
		/**
		 * Ordered list of mappings sources in which this is indexed at [position].
		 *
		 * e.g.
		 *
		 * # Standard FabricMC Usage
		 *
		 * 1. *AccessWidener* (added by Fabric patcher if any mod dependencies are configured)
		 *     - namespace: official
		 * 2. **Fabric Intermediary** (optional)
		 * 3. *AccessWidener* (added by Fabric patcher)
		 *     - namespace: intermediary
		 * 4. **Yarn**
		 * 5. *AccessWidener* (added by Fabric patcher if one is configured in `fabric.mod.json`)
		 *     - namespace: named
		 * 6. *Interface Injection* (added by Fabric patcher if any are configured in `fabric.mod.json`)
		 */
		sources: List<MappingsSource>
	): List<MappingsSource> = super.rectifyList(position, sources)
}
