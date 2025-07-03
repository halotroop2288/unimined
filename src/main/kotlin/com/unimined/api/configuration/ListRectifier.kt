package com.unimined.api.configuration

/**
 * A class that can adjust a list of its own type.
 *
 * @author halotroop2288
 * @since 2.0.0
 * @see [com.unimined.api.configuration.patcher.PatcherConfiguration]
 */
interface ListRectifier<T : ListRectifier<T>> {
	/**
	 * Reads the list and validates its position within.
	 *
	 * The returned list may have more entries added if needed, but none should be removed.
	 *
	 * This should throw an error if the position can't be rectified.
	 *
	 * @return the rectified list.
	 * @throws IllegalArgumentException if this entry isn't in the list.
	 * @since 2.0.0
	 */
	fun rectifyList(
		/**
		 * The position of this patcher in the list of applied patchers.
		 *
		 * When this patcher is applied, the patchers before it will already have been applied.
		 *
		 * e.g. `2` if this is the third patcher (arrays are zero-indexed)
		 */
		position: Int,
		entries: List<T>,
	): List<T> = if (entries[position] == this) entries
	else throw IllegalArgumentException("Invalid position. Current entry not found.")
}
