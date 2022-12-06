package com.shapes.expression

import com.notkamui.keval.Keval

internal class CalculateExpression {
	operator fun invoke(expression: Expression): Double {
		return Keval.eval(expression.expression)
	}
}
