package com.space.element.domain.model

import com.space.element.presentation.main.model.Operator

sealed interface ExpressionItem {
	data class ElementItem(val element: Element) : ExpressionItem {
		override fun toString() = "(${element.value})"
	}

	data class NumberItem(val number: Char) : ExpressionItem {
		override fun toString() = number.toString()
	}

	data class OperatorItem(val operator: Operator) : ExpressionItem {
		override fun toString() = operator.symbol.toString()
	}
}
