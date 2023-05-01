package com.space.element.presentation.main.model

enum class KeyboardButton(val symbol: String, val type: KeyboardButtonType) {
	Zero(symbol = "0", type = KeyboardButtonType.Number),
	One(symbol = "1", type = KeyboardButtonType.Number),
	Two(symbol = "2", type = KeyboardButtonType.Number),
	Three(symbol = "3", type = KeyboardButtonType.Number),
	Four(symbol = "4", type = KeyboardButtonType.Number),
	Five(symbol = "5", type = KeyboardButtonType.Number),
	Six(symbol = "6", type = KeyboardButtonType.Number),
	Seven(symbol = "7", type = KeyboardButtonType.Number),
	Eight(symbol = "8", type = KeyboardButtonType.Number),
	Nine(symbol = "9", type = KeyboardButtonType.Number),
	Delete(symbol = "<-", type = KeyboardButtonType.Delete),
	Dot(symbol = ".", type = KeyboardButtonType.Dot),
	Open(symbol = Operator.Open.symbol, type = KeyboardButtonType.Parentheses),
	Close(symbol = Operator.Close.symbol, type = KeyboardButtonType.Parentheses),
	Addition(symbol = Operator.Addition.symbol, type = KeyboardButtonType.Operator),
	Subtraction(symbol = Operator.Subtraction.symbol, type = KeyboardButtonType.Operator),
	Multiplication(symbol = Operator.Multiplication.symbol, type = KeyboardButtonType.Operator),
	Division(symbol = Operator.Division.symbol, type = KeyboardButtonType.Operator),
	Function(symbol = "f", type = KeyboardButtonType.Function),
	Equal(symbol = "=", type = KeyboardButtonType.Equal)
}
