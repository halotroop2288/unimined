package com.unimined.mappings.configuration.patcher.remapper

import com.unimined.api.EnvironmentComponent
import com.unimined.api.configuration.patcher.PatcherConfiguration

/**
 * # Remapper Patcher Configuration
 *
 * This describes how Unimined should resolve and apply (de-)obfuscation mappings to game files.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
class RemapperConfiguration(
	/**
	 * An ordered list of mappings set sources that describes the order in which mappings should be applied.
	 *
	 * Later elements in the list will be applied to anything not covered by the earlier elements.
	 *
	 * If the mappings have been exhausted, names will fall back to obfuscated "official" names.
	 * It is therefore desirable, but not required to always add an intermediary mappings source at the end of the list.
	 *
	 * e.g.
	 *
	 * ## Standard Parchment Usage
	 *
	 * 1. Mojmap
	 * 2. Parchment
	 *
	 * - Mojmap names will be applied, applying the names used in the official source code
	 * - Because Mojang does not provide them, Parchment comments and parameter names will be applied.
	 * - Because Mojmap has been applied, nothing will fall back to the obfuscated "official" names.
	 *
	 * ## Legacy Fallback Strategy
	 *
	 * 1. Legacy Yarn
	 * 2. Feather
	 * 3. MCP
	 * 4. Ploceus
	 * 5. AccessWidener
	 *
	 * - Access Wideners will be applied whenever they are needed according to their configuration.
	 * - Legacy Yarn names and comments will be applied
	 * - Any names that have not yet been mapped which would normally fall back to Legacy Fabric Intermediary
	 * will then fall back to Feather names.
	 * - Any names that have not yet been mapped which would normally fall back to Ploceus
	 * will then fall back to MCP names.
	 * - Any names that have not yet been mapped which would normally fall back to Searge
	 * will then fall back to Ploceus intermediate names.
	 */
	val sources: Array<MappingsSource> = arrayOf()
) : PatcherConfiguration(
	patcherName = "Remapper",
	*sources,
) {
	val environments: Set<EnvironmentComponent> by lazy { this.typed(EnvironmentComponent::class) }

	override fun validate(): Boolean = super.validate() && environments.all { it.validate() }
}
