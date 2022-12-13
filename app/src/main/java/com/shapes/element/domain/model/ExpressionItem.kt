package com.shapes.element.domain.model

import com.shapes.element.presentation.main.keyboard.KeyboardOperator

sealed class ExpressionItem {
	data class OperatorItem(val operator: KeyboardOperator) : ExpressionItem()
	data class ElementItem(val element: Element) : ExpressionItem()
	data class NumberItem(val number: Int) : ExpressionItem()
}
