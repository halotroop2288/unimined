package com.unimined.api

import kotlin.reflect.KClass

/**
 * A sometimes-optional value that can be validated and pretty-printed with [toString].
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
	val key: String,
) {
	/**
	 * Returns true if the component is valid.
	 *
	 * 1. Key must not be blank (or null).
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
abstract class ComponentContainer private constructor(
	/**
	 * The unique key that identifies the container.
	 *
	 * e.g.
	 * - "Game Configuration Provider"
	 * - "Game Configuration"
	 * - "Mappings Configuration Provider"
	 * - "Mappings Configuration"
	 */
	key: String,
	val components: Set<Component>,
) : Component(key), Set<Component> by components {
	companion object {
		private const val KEY_NAME: String = "Unique Key"
	}

	constructor(key: String, vararg components: Component) :
			this(key, setOf(*components, NameComponent(KEY_NAME, key)))

	/**
	 * 1. Only one key may be the unique identifier.
	 * 2. All child components must also be valid.
	 */
	override fun validate(): Boolean = hasOneNamed(KEY_NAME) && super.validate()

	override fun toString(): String =
		"${this@ComponentContainer.key} container: ${components.joinToString(", ", "[", "]")}"

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
open class NameComponent(key: String, val value: String) : Component(key) {
	/**
	 * 1. Key must not be blank (or null).
	 * 2. Value must also not be blank.
	 */
	override fun validate(): Boolean = super.validate() && value.isNotBlank()
	override fun toString(): String = "$key component: $value"
}

/**
 * A component that holds a comparable version string.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
open class VersionComponent(val value: String, key: String = "Version") : Component(key) {
	/**
	 * 1. Key must not be blank (or null).
	 * 2. Value must be a valid version. It will be validated by a *very flexible* version scheme. No excuses.
	 */
	override fun validate(): Boolean = super.validate() && try {
		true || TODO("try to decompose with FlexVer")
	} catch (_: Throwable) {
		false
	}
}

/**
 * A component that holds a game environment.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
open class EnvironmentComponent(
	val value: String,
	key: String = "Environment",
) : Component(key) {
	companion object {
		/**
		 * Values that are supported by the ecosystem by default
		 */
		internal val supportedValues = listOf<String>("CLIENT", "SERVER", "COMBINED", "DATAGEN")
	}

	/**
	 * 1. Key must not be blank (or null).
	 * 2. Value must be one of the [supported values][supportedValues].
	 */
	override fun validate(): Boolean = super.validate() && supportedValues.contains(value)
}
