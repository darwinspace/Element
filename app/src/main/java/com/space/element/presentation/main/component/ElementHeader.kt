package com.space.element.presentation.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.model.ExpressionResultState

@Composable
fun ElementHeader(
	modifier: Modifier = Modifier,
	expression: List<ExpressionItem>,
	expressionResultState: ExpressionResultState,
	expressionCursorPosition: Int,
	onExpressionCursorPositionChange: (Int) -> Unit
) {
	Surface(
		modifier = modifier,
		tonalElevation = 6.dp
	) {
		Column {
			ElementExpression(
				expression = expression,
				expressionCursorPosition = expressionCursorPosition,
				onExpressionCursorPositionChange = onExpressionCursorPositionChange
			)

			ElementExpressionResult(
				expressionResultState = expressionResultState
			)
		}
	}
}
