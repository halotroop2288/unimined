package com.unimined.mappings.provider.patcher

import com.unimined.api.LazyServices
import com.unimined.api.provider.mappings.MappingsSourceProvider
import com.unimined.api.provider.patcher.PatcherProvider

open class RemapperPatcherProvider: PatcherProvider("Remapper") {
	val mappingsProviders: Set<MappingsSourceProvider> by LazyServices(MappingsSourceProvider::class)
}
