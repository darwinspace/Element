package com.shapes.element.presentation.main.keyboard

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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

	fun getType(): KeyboardButtonType {
		return when (this) {
			is NumberButton -> KeyboardButtonType.Default
			is OperatorButton -> {
				when (operator) {
					KeyboardOperator.Delete -> KeyboardButtonType.Delete
					KeyboardOperator.Open,
					KeyboardOperator.Close -> KeyboardButtonType.Parentheses
					KeyboardOperator.Multiply,
					KeyboardOperator.Divide,
					KeyboardOperator.Add,
					KeyboardOperator.Subtract -> KeyboardButtonType.Operator
					KeyboardOperator.Equal -> KeyboardButtonType.Equal
				}
			}
		}
	}

	@Composable
	fun getColor(): Color {
		return when (getType()) {
			KeyboardButtonType.Delete -> MaterialTheme.colorScheme.tertiaryContainer
			KeyboardButtonType.Parentheses,
			KeyboardButtonType.Operator -> MaterialTheme.colorScheme.secondaryContainer
			KeyboardButtonType.Equal -> MaterialTheme.colorScheme.primary
			KeyboardButtonType.Default -> MaterialTheme.colorScheme.surface
		}
	}
}
