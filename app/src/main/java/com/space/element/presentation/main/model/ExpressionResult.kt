package com.space.element.presentation.main.model

sealed interface ExpressionResult {
	data class Value(val value: Double) : ExpressionResult
	data class Error(val exception: Exception) : ExpressionResult
	data object Empty : ExpressionResult
}
