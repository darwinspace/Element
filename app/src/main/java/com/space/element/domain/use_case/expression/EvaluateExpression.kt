package com.space.element.domain.use_case.expression

import com.notkamui.keval.Keval.Companion.eval
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.model.ExpressionResult

class EvaluateExpression {
	operator fun invoke(data: List<ExpressionItem>): ExpressionResult {
		return try {
			val expression = data.joinToString(String())
			val value = eval(expression)
			ExpressionResult.Value(value)
		} catch (exception: Exception) {
			ExpressionResult.Error(exception)
		}
	}
}
