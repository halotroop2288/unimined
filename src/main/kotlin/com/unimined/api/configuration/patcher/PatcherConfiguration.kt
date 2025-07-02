package com.unimined.api.configuration.patcher

import com.unimined.api.ComponentContainer
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
) : ComponentContainer(patcherName) {
	/**
	 * This should return `0` if the position is valid,
	 * or throw an error if the position can't be rectified.
	 *
	 * @return The relative position this patcher wants to be moved to.
	 * @throws IllegalArgumentException if this patcher isn't in the list.
	 * @since 2.0.0
	 */
	protected fun validatePosition(
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
		 * - AccessWidener(named = false),
		 * - Remapper(configuration = ...),
		 * - AccessWidener(named = true),
		 * - ForgeModLoader(version = ...),
		 * - MinecraftForge(version = ...),
		 */
		patchers: List<PatcherConfiguration>,
	): (Int) = if (patchers[position] == this) 0
	else throw IllegalArgumentException("Invalid position. Configuration not found.")
}
