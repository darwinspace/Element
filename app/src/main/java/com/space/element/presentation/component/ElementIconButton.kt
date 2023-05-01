package com.space.element.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ElementIconButton(
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
	content: @Composable () -> Unit
) {
	FilledTonalIconButton(
		modifier = modifier
			.border(
				border = BorderStroke(
					width = 2.dp,
					color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.1f)
				),
				shape = CircleShape
			)
			.size(48.dp),
		onClick = onClick,
		content = content
	)
}