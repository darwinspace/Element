package com.shapes.element

import com.shapes.element.base.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ElementUnitTest {
	@Test
	fun `Expression in a OperationElement is correct`() {
		val element = NumberElement(2) * (NumberElement(2) * NumberElement(20)
				+ NumberElement(1))

		val result = element.getResult()

		assertEquals("Expression was '2 * ( 2 * 20 + 1)'", 82.0, result, 0.1)
	}

	@Test
	fun `NumberElement addition in a OperationElement is correct`() {
		val a: Element = NumberElement(12)
		val b: Element = NumberElement(24)

		val addition = a + b

		val result = addition.getResult()

		assertEquals(12.0 + 24.0, result, 0.1)
	}

	@Test
	fun `NumberElement subtraction in a OperationElement is correct`() {
		val a: Element = NumberElement(24)
		val b: Element = NumberElement(32)

		val subtraction = a - b

		val result = subtraction.getResult()

		assertEquals(24.0 - 32.0, result, 0.1)
	}

	@Test
	fun `RootElement calculation of expression is right`() {
		/*
		*       RootExpression = 2 * (2 * 20 + (1 * (2 + 1) + 2 * 3))
		* 		Result = 98
		* */

		val expression = "2 * (2 * 20 + (1 * (2 + 1) + 2 * 3))"
		val root = RootElement(expression)

		val result = root.getResult()

		assertEquals(98.0, result, 0.1)
	}
}