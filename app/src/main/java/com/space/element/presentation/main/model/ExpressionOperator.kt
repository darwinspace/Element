package com.space.element.presentation.main.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed class ExpressionOperator(val symbol: Char, val type: Type) {
	enum class Type {
		Parentheses, Arithmetic
	}

	data object Open : ExpressionOperator(type = Type.Parentheses, symbol = '(')
	data object Close : ExpressionOperator(type = Type.Parentheses, symbol = ')')
	data object Dot : ExpressionOperator(type = Type.Arithmetic, symbol = '.')
	data object Addition : ExpressionOperator(type = Type.Arithmetic, symbol = '+')
	data object Subtraction : ExpressionOperator(type = Type.Arithmetic, symbol = '-')
	data object Multiplication : ExpressionOperator(type = Type.Arithmetic, symbol = '*')
	data object Division : ExpressionOperator(type = Type.Arithmetic, symbol = '/')

	@Composable
	fun getColor(): Color {
		return when (type) {
			Type.Arithmetic -> MaterialTheme.colorScheme.primary
			Type.Parentheses -> MaterialTheme.colorScheme.tertiary
		}
	}
}
