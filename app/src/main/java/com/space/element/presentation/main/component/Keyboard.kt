package com.space.element.presentation.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardBackspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.space.element.presentation.`interface`.theme.ElementTheme
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.KeyboardButton.Type

@Preview
@Composable
fun KeyboardPreview() {
	ElementTheme {
		Keyboard(
			onButtonClick = {
				throw NotImplementedError()
			}
		)
	}
}

@Preview
@Composable
fun KeyboardVariantPreview() {
	ElementTheme {
		KeyboardVariant(
			onButtonClick = {
				throw NotImplementedError()
			}
		)
	}
}

@Composable
fun Keyboard(
	modifier: Modifier = Modifier,
	contentGap: Dp = 0.dp,
	contentPadding: PaddingValues = PaddingValues(0.dp),
	onButtonClick: (KeyboardButton) -> Unit
) {
	val buttonModifier = Modifier
	Column(
		modifier = modifier.padding(contentPadding),
		verticalArrangement = Arrangement.spacedBy(contentGap)
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Clear,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Open,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Close,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Division,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Seven,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Eight,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Nine,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Multiplication,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Four,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Five,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Six,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Subtraction,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.One,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Two,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Three,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Addition,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Dot,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Zero,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Delete,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				Icon(
					imageVector = Icons.AutoMirrored.Outlined.KeyboardBackspace,
					contentDescription = null
				)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Equal,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}
	}
}

@Composable
fun KeyboardVariant(
	modifier: Modifier = Modifier,
	contentGap: Dp = 16.dp,
	contentPadding: PaddingValues = PaddingValues(16.dp),
	onButtonClick: (KeyboardButton) -> Unit
) {
	val buttonModifier = Modifier
	Column(
		modifier = modifier.padding(contentPadding),
		verticalArrangement = Arrangement.spacedBy(contentGap)
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Seven,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Eight,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Nine,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Division,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Clear,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Four,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Five,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Six,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Open,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Close,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.One,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Two,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Three,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Subtraction,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Multiplication,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(contentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Dot,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Zero,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Delete,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				Icon(
					imageVector = Icons.AutoMirrored.Outlined.KeyboardBackspace,
					contentDescription = null
				)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Addition,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Equal,
				modifier = buttonModifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}
	}
}

@Composable
fun KeyboardButton(
	keyboardButton: KeyboardButton,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	containerColor: Color = keyboardButton.getKeyboardButtonSurfaceColor(),
	contentColor: Color = contentColorFor(containerColor),
	onClick: (KeyboardButton) -> Unit,
	content: @Composable RowScope.(KeyboardButton) -> Unit
) {
	val hapticFeedback = LocalHapticFeedback.current
	Button(
		modifier = modifier,
		shape = CircleShape,
		enabled = enabled,
		colors = ButtonDefaults.buttonColors(
			containerColor = containerColor,
			contentColor = contentColor
		),
		border = BorderStroke(
			width = 2.dp,
			color = contentColor.copy(alpha = 0.1f)
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
fun KeyboardButtonSymbol(symbol: Char) {
	Text(
		text = symbol.toString(),
		style = MaterialTheme.typography.titleLarge
	)
}

@Composable
fun KeyboardButton.getKeyboardButtonSurfaceColor(): Color {
	return when (type) {
		Type.Dot,
		Type.Delete,
		Type.Number,
		Type.Operator,
		Type.Parentheses -> MaterialTheme.colorScheme.surface

		Type.Clear -> MaterialTheme.colorScheme.tertiaryContainer
		Type.Equal -> MaterialTheme.colorScheme.primary
	}
}
