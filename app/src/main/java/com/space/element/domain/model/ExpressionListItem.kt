package com.space.element.domain.model

sealed interface ExpressionListItem {
	data class ElementItem(val element: Element) : ExpressionListItem {
		override fun toString() = "(${element.value})"
	}

	data class NumberItem(val number: Char) : ExpressionListItem {
		override fun toString() = number.toString()
	}

	data class OperatorItem(val operator: Operator) : ExpressionListItem {
		override fun toString() = operator.symbol.toString()
	}

	data class FunctionItem(val function: Function) : ExpressionListItem {
		override fun toString(): String = function.name
	}
}
