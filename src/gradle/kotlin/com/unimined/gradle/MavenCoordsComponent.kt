package com.unimined.gradle

import com.unimined.api.FileSourceComponent
import xyz.wagyourtail.commonskt.maven.MavenCoords
import java.io.InputStream

/**
 * A component that can be resolved to a file via a Maven artifact.
 *
 * @author halotroop2288
 * @since 2.0.0
 */
class MavenCoordsComponent(
	val coordinates: MavenCoords,
) : FileSourceComponent(coordinates.toString()) {
	constructor(value: String): this(MavenCoords(value))
	constructor(
		group: String? = "", artifact: String, version: String? = null,
		classifier: String? = null, extension: String? = null
	): this(MavenCoords(
		group ?: TODO("https://github.com/wagyourtail/commons/pull/1"),
		artifact, version, classifier, extension
	))

	override fun invoke(): InputStream = TODO("Not yet implemented")
}
