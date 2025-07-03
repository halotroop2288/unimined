package com.unimined.api.configuration.patcher

import com.unimined.api.Component
import com.unimined.api.ComponentContainer
import com.unimined.api.configuration.ListRectifier
import com.unimined.api.provider.patcher.PatcherProvider

/**
 * # Patcher Configuration
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class PatcherConfiguration(
	/**
	 * A unique identifier for the patcher.
	 *
	 * This needs to be passed in by the [PatcherProvider].
	 *
	 * **Note: Case-sensitive!**
	 *
	 * @since 2.0.0
	 */
	patcherName: String,
	vararg components: Component
) : ComponentContainer("$patcherName Configuration", *components), ListRectifier<PatcherConfiguration> {
	/**
	 * Reads the list of patchers and validates its position within.
	 *
	 * The returned list may have more patchers added if needed, but none should be removed.
	 *
	 * This should throw an error if the position can't be rectified
	 * or if an incompatible patcher is found in the list.
	 *
	 * @return the rectified list of patchers.
	 * @throws IllegalArgumentException if this patcher isn't in the list.
	 * @since 2.0.0
	 */
	override fun rectifyList(
		/**
		 * The position of this patcher in the list of applied patchers.
		 *
		 * When this patcher is applied, the patchers before it will already have been applied.
		 *
		 * e.g. `2` if this is the third patcher (arrays are zero-indexed)
		 */
		position: Int,
		/**
		 * Ordered list of patcher configurations in which this is indexed at [position].
		 *
		 * e.g.
		 *
		 * # Standard FabricMC Usage
		 *
		 * - Remapper (optional
		 * - ForgeModLoader (added by Minecraft Forge)
		 * - Minecraft Forge
		 *
		 * # Standard Minecraft Forge Usage
		 *
		 * - Forge Mod Loader (added by Minecraft Forge)
		 * - **Minecraft Forge**
		 * - *Un-mapper* (added by Minecraft Forge if Remapper is present)
		 * - **Remapper**
		 */
		patchers: List<PatcherConfiguration>,
	): List<PatcherConfiguration> = super.rectifyList(position, patchers)
}
