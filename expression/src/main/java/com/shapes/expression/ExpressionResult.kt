package com.shapes.expression

sealed class ExpressionResult {
	data class Value(val value: Double) : ExpressionResult()
	data class ExpressionException(val exception: java.lang.Exception) : ExpressionResult()
}
