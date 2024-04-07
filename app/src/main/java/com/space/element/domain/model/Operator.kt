package com.space.element.domain.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed class Operator(val symbol: Char, val type: Type) {
	enum class Type {
		Parentheses, Arithmetic
	}

	data object Open : Operator(type = Type.Parentheses, symbol = '(')
	data object Close : Operator(type = Type.Parentheses, symbol = ')')
	data object Dot : Operator(type = Type.Arithmetic, symbol = '.')
	data object Addition : Operator(type = Type.Arithmetic, symbol = '+')
	data object Subtraction : Operator(type = Type.Arithmetic, symbol = '-')
	data object Multiplication : Operator(type = Type.Arithmetic, symbol = '*')
	data object Division : Operator(type = Type.Arithmetic, symbol = '/')

	@Composable
	fun getColor(): Color {
		return when (type) {
			Type.Arithmetic -> MaterialTheme.colorScheme.primary
			Type.Parentheses -> MaterialTheme.colorScheme.tertiary
		}
	}
}
