package com.space.element

import com.space.element.domain.model.ExpressionListItem
import com.space.element.domain.model.ExpressionListItem.FunctionItem
import com.space.element.domain.model.ExpressionListItem.NumberItem
import com.space.element.domain.model.ExpressionListItem.OperatorItem
import com.space.element.domain.model.Operator
import com.space.element.domain.use_case.expression.EvaluateExpression
import com.space.element.presentation.main.model.ExpressionResultState
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

	@Test
	fun Evaluate() {
		// function(8) -> 16
		val list = buildList<ExpressionListItem> {
			val function = com.space.element.domain.model.Function("function", "x*2")
			this += FunctionItem(function)
			this += OperatorItem(Operator.Open)
			this += NumberItem('8')
			this += OperatorItem(Operator.Close)
		}
		val eval = EvaluateExpression()
		when(val result = eval(list)) {
			ExpressionResultState.Empty -> TODO()
			is ExpressionResultState.Error -> {
				println("Result = ${result.exception}")
			}
			is ExpressionResultState.Value -> {
				println("Result = ${result.value}")
				assertEquals(result.value, 16.0, 0.0)
			}
		}
	}
}
