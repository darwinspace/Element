package com.space.element.presentation.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.space.element.presentation.main.model.ExpressionResultState

@Composable
fun ElementHeader(modifier: Modifier = Modifier) {
	val tonalElevation = 3.dp

	Surface(
		modifier = modifier,
		tonalElevation = tonalElevation
	) {
		Column {
			ElementExpression(expression = emptyList(), expressionCursorPosition = 0)
			ElementExpressionResult(result = ExpressionResultState.Value(0.0))
		}
	}
}
