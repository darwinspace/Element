package com.shapes.element.presentation.main.viewmodel

import com.shapes.expression.ExpressionResult

sealed class ExpressionResultState {
	data class Value(val result: ExpressionResult) : ExpressionResultState()
	object Empty : ExpressionResultState()
}
