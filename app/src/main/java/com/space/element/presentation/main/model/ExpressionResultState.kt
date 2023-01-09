package com.space.element.presentation.main.model

sealed class ExpressionResultState {
	data class Value(val result: ExpressionResult) : ExpressionResultState()
	object Empty : ExpressionResultState()
}
