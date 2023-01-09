package com.space.element.presentation.main.model

sealed class ExpressionResult {
	data class Value(val value: Double) : ExpressionResult()
	data class ExpressionException(val exception: Exception) : ExpressionResult()
}
