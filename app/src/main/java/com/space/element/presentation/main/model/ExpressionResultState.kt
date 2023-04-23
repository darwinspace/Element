package com.space.element.presentation.main.model

sealed class ExpressionResultState {
	data class Value(val value: Double) : ExpressionResultState()
	data class Error(val exception: Exception) : ExpressionResultState()
	object Empty : ExpressionResultState()
}
