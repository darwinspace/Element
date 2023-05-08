package com.space.element.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalIconButton
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
		modifier = modifier.size(48.dp),
		onClick = onClick,
		content = content
	)
}