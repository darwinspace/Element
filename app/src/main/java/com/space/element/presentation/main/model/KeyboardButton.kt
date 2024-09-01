package com.space.element.presentation.main.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.space.element.domain.model.Operator

sealed class KeyboardButton(val symbol: Char, val type: Type) {
	enum class Type {
		Number, Operator, Delete, Clear, Parentheses, Dot, Equal
	}
	
	data object Zero : KeyboardButton(symbol = '0', type = Type.Number)
	data object One : KeyboardButton(symbol = '1', type = Type.Number)
	data object Two : KeyboardButton(symbol = '2', type = Type.Number)
	data object Three : KeyboardButton(symbol = '3', type = Type.Number)
	data object Four : KeyboardButton(symbol = '4', type = Type.Number)
	data object Five : KeyboardButton(symbol = '5', type = Type.Number)
	data object Six : KeyboardButton(symbol = '6', type = Type.Number)
	data object Seven : KeyboardButton(symbol = '7', type = Type.Number)
	data object Eight : KeyboardButton(symbol = '8', type = Type.Number)
	data object Nine : KeyboardButton(symbol = '9', type = Type.Number)
	data object Clear : KeyboardButton(symbol = 'C', type = Type.Clear)
	data object Dot : KeyboardButton(symbol = '.', type = Type.Dot)
	data object Open :
		KeyboardButton(symbol = Operator.Open.symbol, type = Type.Parentheses)

	data object Close :
		KeyboardButton(symbol = Operator.Close.symbol, type = Type.Parentheses)

	data object Addition :
		KeyboardButton(symbol = Operator.Addition.symbol, type = Type.Operator)

	data object Subtraction :
		KeyboardButton(symbol = Operator.Subtraction.symbol, type = Type.Operator)

	data object Multiplication :
		KeyboardButton(symbol = Operator.Multiplication.symbol, type = Type.Operator)

	data object Division :
		KeyboardButton(symbol = Operator.Division.symbol, type = Type.Operator)

	data object Delete : KeyboardButton(symbol = '?', type = Type.Delete)
	data object Equal : KeyboardButton(symbol = '=', type = Type.Equal)
}
