package com.space.element.presentation.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.model.ExpressionResultState

@Composable
fun ElementHeader(
	modifier: Modifier = Modifier,
	expression: () -> SnapshotStateList<ExpressionItem>,
	expressionResultState: () -> ExpressionResultState,
	expressionCursorPosition: () -> Int,
	onExpressionCursorPositionChange: (Int) -> Unit
) {
	Surface(
		modifier = modifier,
		shape = MaterialTheme.shapes.extraLarge.copy(
			topStart = CornerSize(0.dp),
			topEnd = CornerSize(0.dp)
		),
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
