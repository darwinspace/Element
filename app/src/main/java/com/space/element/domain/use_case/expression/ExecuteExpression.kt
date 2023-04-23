package com.space.element.domain.use_case.expression

import com.notkamui.keval.KevalZeroDivisionException
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.model.Expression
import com.space.element.presentation.main.model.ExpressionResult

class ExecuteExpression {
	operator fun invoke(data: List<ExpressionItem>): ExpressionResult {
		val calculate = CalculateExpression()
		return try {
			val rawExpression = data.joinToString("")
			val expression = Expression(rawExpression)
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
