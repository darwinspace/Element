package com.space.element.presentation.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardBackspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.KeyboardButton.Type
import com.space.element.presentation.`interface`.theme.ElementTheme

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
	contentGap: Dp = 16.dp,
	contentPadding: PaddingValues = PaddingValues(16.dp),
	onButtonClick: (KeyboardButton) -> Unit
) {
	Surface(modifier = modifier) {
		KeyboardContent(
			keyboardContentPadding = contentPadding,
			keyboardContentGap = contentGap,
			onButtonClick = onButtonClick
		)
	}
}

@Composable
private fun KeyboardContent(
	keyboardContentPadding: PaddingValues,
	keyboardContentGap: Dp,
	onButtonClick: (KeyboardButton) -> Unit,
) {
	Column(
		modifier = Modifier.padding(keyboardContentPadding),
		verticalArrangement = Arrangement.spacedBy(keyboardContentGap)
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Clear,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Open,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Close,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Division,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Seven,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Eight,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Nine,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Multiplication,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Four,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Five,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Six,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Subtraction,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.One,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Two,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Three,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Addition,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Dot,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Zero,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Delete,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardBackspace, contentDescription = null)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Equal,
				modifier = Modifier.weight(1f),
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
	Surface(modifier = modifier) {
		KeyboardVariantContent(
			keyboardContentPadding = contentPadding,
			keyboardContentGap = contentGap,
			onButtonClick = onButtonClick
		)
	}
}

@Composable
private fun KeyboardVariantContent(
	keyboardContentPadding: PaddingValues,
	keyboardContentGap: Dp,
	onButtonClick: (KeyboardButton) -> Unit,
) {
	Column(
		modifier = Modifier.padding(keyboardContentPadding),
		verticalArrangement = Arrangement.spacedBy(keyboardContentGap)
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Seven,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Eight,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Nine,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Division,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Clear,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Four,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Five,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Six,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Open,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Close,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.One,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Two,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Three,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Subtraction,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Multiplication,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			KeyboardButton(
				keyboardButton = KeyboardButton.Dot,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Zero,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Delete,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardBackspace, contentDescription = null)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Addition,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				KeyboardButtonSymbol(symbol = it.symbol)
			}

			KeyboardButton(
				keyboardButton = KeyboardButton.Equal,
				modifier = Modifier.weight(1f),
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
		modifier = modifier.aspectRatio(1f),
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
