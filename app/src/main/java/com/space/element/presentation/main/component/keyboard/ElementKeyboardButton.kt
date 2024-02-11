package com.space.element.presentation.main.component.keyboard

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.KeyboardButtonType

@Composable
private fun KeyboardButton.getSurfaceColor(): Color {
	val elevation = 1.dp
	return when (type) {
		KeyboardButtonType.Dot,
		KeyboardButtonType.Delete,
		KeyboardButtonType.Number -> MaterialTheme.colorScheme.surfaceColorAtElevation(elevation)

		KeyboardButtonType.Operator,
		KeyboardButtonType.Parentheses -> MaterialTheme.colorScheme.secondaryContainer

		KeyboardButtonType.Clear -> MaterialTheme.colorScheme.tertiaryContainer
		KeyboardButtonType.Equal -> MaterialTheme.colorScheme.primary
	}
}

@Composable
fun ElementKeyboardButton(
	keyboardButton: KeyboardButton,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	containerColor: Color = keyboardButton.getSurfaceColor(),
	contentColor: Color = contentColorFor(containerColor),
	onClick: (KeyboardButton) -> Unit,
	content: @Composable RowScope.(KeyboardButton) -> Unit
) {
	val hapticFeedback = LocalHapticFeedback.current
	Button(
		modifier = modifier.aspectRatio(1f),
		shape = CircleShape,
		enabled = enabled,
		colors = ButtonDefaults.buttonColors(
			containerColor = containerColor,
			contentColor = contentColor
		),
		onClick = {
			onClick(keyboardButton)
			hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
		}
	) {
		content(keyboardButton)
	}
}

@Composable
fun ElementKeyboardButtonText(text: String) {
	Text(text = text, style = MaterialTheme.typography.headlineMedium)
}

@Composable
fun ElementKeyboardButtonSymbol(symbol: Char) {
	ElementKeyboardButtonText(text = symbol.toString())
}
