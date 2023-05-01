package com.space.element.presentation.main.component.keyboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.KeyboardButtonType

@Composable
private fun KeyboardButton.getSurfaceColor(): Color {
	val elevation = 1.dp
	return when (type) {
		KeyboardButtonType.Dot,
		KeyboardButtonType.Function,
		KeyboardButtonType.Number -> MaterialTheme.colorScheme.surfaceColorAtElevation(elevation)

		KeyboardButtonType.Operator,
		KeyboardButtonType.Parentheses -> MaterialTheme.colorScheme.primaryContainer

		KeyboardButtonType.Delete -> MaterialTheme.colorScheme.tertiaryContainer
		KeyboardButtonType.Equal -> MaterialTheme.colorScheme.primary
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ElementKeyboardButton(
	keyboardButton: KeyboardButton,
	modifier: Modifier = Modifier,
	color: Color = keyboardButton.getSurfaceColor(),
	borderColor: Color = contentColorFor(color).copy(alpha = 0.1f),
	onClick: (KeyboardButton) -> Unit,
	onLongClick: (KeyboardButton) -> Unit,
	content: @Composable BoxScope.(KeyboardButton) -> Unit
) {
	Surface(
		modifier = modifier
			.clip(MaterialTheme.shapes.medium)
			.combinedClickable(
				role = Role.Button,
				onClick = {
					onClick(keyboardButton)
				},
				onLongClick = {
					onLongClick(keyboardButton)
				}
			),
		shape = MaterialTheme.shapes.medium,
		color = color,
		border = BorderStroke(
			width = 2.dp,
			color = borderColor
		)
	) {
		Box(
			modifier = Modifier
				.height(72.dp)
				.defaultMinSize(
					minWidth = 40.dp, minHeight = 40.dp
				),
			contentAlignment = Alignment.Center
		) {
			content(keyboardButton)
		}
	}
}

@Composable
fun ElementKeyboardButtonText(text: String) {
	Text(
		text = text,
		style = MaterialTheme.typography.titleMedium
	)
}
