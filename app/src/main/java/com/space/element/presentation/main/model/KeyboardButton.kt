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
	Dot(symbol = ".", type = KeyboardButtonType.Number),
	Delete(symbol = "<-", type = KeyboardButtonType.Delete),
	Open(symbol = "(", type = KeyboardButtonType.Parentheses),
	Close(symbol = ")", type = KeyboardButtonType.Parentheses),
	Divide(symbol = "/", type = KeyboardButtonType.Operator),
	Add(symbol = "+", type = KeyboardButtonType.Operator),
	Subtract(symbol = "-", type = KeyboardButtonType.Operator),
	Multiply(symbol = "*", type = KeyboardButtonType.Operator),
	Function(symbol = "ƒ", type = KeyboardButtonType.Function),
	Equal(symbol = "=", type = KeyboardButtonType.Equal)
}
