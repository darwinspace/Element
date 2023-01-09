package com.space.element.domain.use_case.expression

import com.notkamui.keval.KevalZeroDivisionException
import com.space.element.presentation.main.model.Expression
import com.space.element.presentation.main.model.ExpressionResult

class ExecuteExpression {
	operator fun invoke(expression: Expression): ExpressionResult {
		return try {
			val calculate = CalculateExpression()
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
