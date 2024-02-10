package com.space.element.presentation.main.model

sealed interface ExpressionResultState {
	data class Value(val value: Double) : ExpressionResultState
	data class Error(val exception: Exception) : ExpressionResultState
	data object Empty : ExpressionResultState
}
