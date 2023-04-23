package com.space.element.presentation.main.model

enum class Operator(val symbol: String, val type: OperatorType) {
	Open(type = OperatorType.Parentheses, symbol = "("),
	Close(type = OperatorType.Parentheses, symbol = ")"),
	Dot(type = OperatorType.Arithmetic, symbol = "."),
	Addition(type = OperatorType.Arithmetic, symbol = "+"),
	Subtraction(type = OperatorType.Arithmetic, symbol = "-"),
	Multiplication(type = OperatorType.Arithmetic, symbol = "×"),
	Division(type = OperatorType.Arithmetic, symbol = "/")
}
