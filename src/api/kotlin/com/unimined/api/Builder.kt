package com.unimined.api

/**
 * Importantly, these builders take no extra context than what is provided in their instance.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
interface Builder<T> {
	fun build(): T
}
