package com.space.element.domain.model

import com.space.element.presentation.main.model.KeyboardButton

sealed class ExpressionItem {
	data class OperatorItem(val keyboardButton: KeyboardButton) : ExpressionItem() {
		override fun toString() = keyboardButton.symbol
	}

	data class ElementItem(val element: Element) : ExpressionItem() {
		override fun toString() = "(${element.value})"
	}
}
