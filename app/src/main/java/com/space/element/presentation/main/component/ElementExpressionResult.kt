package com.space.element.presentation.main.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.util.format


@Composable
fun ElementExpressionResult(expressionResultState: ExpressionResultState) {
	if (expressionResultState is ExpressionResultState.Value) {
		ElementExpressionResultValue(expressionResultState)
	}
}

@Composable
private fun ElementExpressionResultValue(expressionResultState: ExpressionResultState.Value) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.horizontalScroll(rememberScrollState())
			.padding(horizontal = 24.dp),
		contentAlignment = Alignment.CenterEnd
	) {
		ElementExpressionResultText(expressionResultState.value)
	}
}

@Composable
fun ElementExpressionResultText(value: Double) {
	Text(
		text = value.format(),
		textAlign = TextAlign.End,
		style = MaterialTheme.typography.headlineMedium
	)
}
