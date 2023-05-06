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
	expressionCursorPosition: Int,
	expressionResultState: ExpressionResultState,
	onExpressionSpaceClick: (Int) -> Unit
) {
	val tonalElevation = 6.dp

	Surface(
		modifier = modifier,
		tonalElevation = tonalElevation
	) {
		Column {
			ElementExpression(
				expression = expression,
				expressionCursorPosition = expressionCursorPosition,
				onExpressionSpaceClick = onExpressionSpaceClick
			)

			ElementExpressionResult(
				expressionResultState = expressionResultState
			)
		}
	}
}
