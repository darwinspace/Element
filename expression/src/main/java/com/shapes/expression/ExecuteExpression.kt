package com.shapes.expression

import com.notkamui.keval.KevalZeroDivisionException

class ExecuteExpression {
	private val calculate by lazy { CalculateExpression() }

	operator fun invoke(expression: Expression): ExpressionResult {
		return try {
			val value = calculate(expression)
			ExpressionResult.Value(value)
		} catch (exception: KevalZeroDivisionException) {
			val arithmeticException = ArithmeticException()
			ExpressionResult.ExpressionException(arithmeticException)
		} catch (exception: Exception) {
			ExpressionResult.ExpressionException(exception)
		}
	}
}
