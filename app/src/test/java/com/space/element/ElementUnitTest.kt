package com.space.element

import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.sqrt

class ElementUnitTest {
	@Test
	fun `Evaluate expression`() {
		val function = object : Function("squareRoot") {
			override fun apply(vararg args: Double): Double {
				val v = args[0]
				return sqrt(v)
			}
		}

		val expressionData = "squareRoot(squareRoot(81))"
		val expression = ExpressionBuilder(expressionData)
			.function(function)
			.build()
		val result = expression.evaluate()
		assertEquals(3.0, result, 0.0)
	}
}
