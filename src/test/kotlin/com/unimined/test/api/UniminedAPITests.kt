package com.unimined.test.api

import com.unimined.api.provider.game.GenericGameProvider
import com.unimined.api.configuration.game.GameConfiguration
import kotlin.test.Test

object UniminedAPITests {
	@Test
	fun testGenericGameProvider() {
		val gameConfiguration: GameConfiguration = GenericGameProvider
			.builder("Test")
			.build()
	}
}