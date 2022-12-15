package com.shapes.element.domain.model

import com.shapes.element.presentation.main.keyboard.KeyboardOperator

sealed class ExpressionItem {
	data class OperatorItem(val operator: KeyboardOperator) : ExpressionItem() {
		override fun toString() = operator.symbol
	}

	data class ElementItem(val element: Element) : ExpressionItem() {
		override fun toString() = "(${element.value})"
	}

	data class NumberItem(val number: String) : ExpressionItem() {
		override fun toString() = number
	}
}
