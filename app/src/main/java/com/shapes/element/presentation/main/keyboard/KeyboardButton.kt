package com.shapes.element.presentation.main.keyboard

sealed class KeyboardButton {
	abstract val text: String

	data class NumberButton(val number: Int) : KeyboardButton() {
		override val text: String
			get() = number.toString()
	}

	data class OperatorButton(val operator: KeyboardOperator) : KeyboardButton() {
		override val text: String
			get() = operator.symbol
	}
}
