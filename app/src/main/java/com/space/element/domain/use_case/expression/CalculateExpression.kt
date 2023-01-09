package com.space.element.domain.use_case.expression

import com.notkamui.keval.Keval
import com.space.element.presentation.main.model.Expression

internal class CalculateExpression {
	operator fun invoke(expression: Expression): Double {
		return Keval.eval(expression.expression)
	}
}
