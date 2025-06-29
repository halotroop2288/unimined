package com.unimined.api

import kotlin.reflect.KClass

/**
 * A sometimes-optional value that can be validated.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class Component(
	/**
	 * An identifier for this tag.
	 *
	 * This can be used to ensure only one component with the key exists during validation,
	 * or to find all the components with the same key.
	 */
	val key: String
) {
	/**
	 * Returns true if the component is valid.
	 *
	 * @since 2.0.0
	 */
	open fun validate(): Boolean = key.isNotBlank()

	override fun toString(): String = "$key component"
	override fun hashCode(): Int = key.hashCode()
	override fun equals(other: Any?): Boolean = this::class.isInstance(other) && (other as Component).key == key
}

/**
 * The base class for all uniquely-identified registered components.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
abstract class ComponentContainer(
	uniqueKey: String,
	val components: Set<Component>
) : Component(uniqueKey), Set<Component> by components {
	override fun validate(): Boolean = hasAnyNamed("Unique Name") && super.validate()
	override fun toString(): String = "$key container: ${components.joinToString(",\n\t", "[", "]")}"

	/**
	 * Returns true if this contains ANY components with the specified type.
	 *
	 * @since 2.0.0
	 */
	fun <T : Component> hasAnyOfType(type: KClass<T>): Boolean = any { type.isInstance(it) }

	/**
	 * Returns true if this contains ANY components with the specified key.
	 *
	 * @since 2.0.0
	 */
	fun hasAnyNamed(key: String): Boolean = any { key == it.key }

	/**
	 * Returns components of the specified component type.
	 *
	 * @since 2.0.0
	 */
	fun <T : Component> ofType(type: KClass<T>): Set<T> = let {
		return@let hashSetOf<T>().let { set ->
			@Suppress("UNCHECKED_CAST")
			components.forEach { if (type.isInstance(it)) set.add(it as T) }
			set
		}
	}

	/**
	 * Returns true if this contains ONLY ONE component with the specified key.
	 *
	 * @since 2.0.0
	 */
	fun named(key: String): List<Component> = filter { key == it.key }

	/**
	 * Returns true if this contains ONLY ONE of the specified component type.
	 *
	 * @since 2.0.0
	 */
	fun <T : Component> hasOneOfType(type: KClass<T>): Boolean = ofType(type).size == 1

	/**
	 * Returns true if this contains ONLY ONE component with the specified key.
	 *
	 * @since 2.0.0
	 */
	fun hasOneNamed(key: String): Boolean = named(key).size == 1
}

/**
 * A component that holds the unique name of another component.
 * The value may not be blank.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
class NameComponent(
	val value: String,
	key: String = "Unique Name",
) : Component(key) {
	override fun validate(): Boolean = super.validate() && value.isNotBlank()

	override fun toString(): String = "$key component: $value"
}
