package com.space.element.presentation.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
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
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ElementKeyboardPreview() {
	ElementTheme {
		ElementKeyboard(
			onButtonClick = {
				throw NotImplementedError()
			}
		)
	}
}

@Preview
@Composable
fun ElementKeyboardVariantPreview() {
	ElementTheme {
		ElementKeyboardVariant(
			onButtonClick = {
				throw NotImplementedError()
			}
		)
	}
}

@Composable
fun ElementKeyboard(
	modifier: Modifier = Modifier,
	contentGap: Dp = 16.dp,
	contentPadding: PaddingValues = PaddingValues(16.dp),
	onButtonClick: (KeyboardButton) -> Unit
) {
	Surface(modifier = modifier) {
		ElementKeyboardContent(
			keyboardContentPadding = contentPadding,
			keyboardContentGap = contentGap,
			onButtonClick = onButtonClick
		)
	}
}

@Composable
private fun ElementKeyboardContent(
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
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Clear,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Open,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Close,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Division,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Seven,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Eight,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Nine,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Multiplication,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Four,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Five,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Six,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Subtraction,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.One,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Two,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Three,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Addition,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Dot,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Zero,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Delete,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				Icon(imageVector = Icons.AutoMirrored.Outlined.Backspace, contentDescription = null)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Equal,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}
	}
}

@Composable
fun ElementKeyboardVariant(
	modifier: Modifier = Modifier,
	contentGap: Dp = 16.dp,
	contentPadding: PaddingValues = PaddingValues(16.dp),
	onButtonClick: (KeyboardButton) -> Unit
) {
	Surface(modifier = modifier) {
		ElementKeyboardVariantContent(
			keyboardContentPadding = contentPadding,
			keyboardContentGap = contentGap,
			onButtonClick = onButtonClick
		)
	}
}

@Composable
private fun ElementKeyboardVariantContent(
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
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Seven,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Eight,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Nine,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Division,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Clear,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Four,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Five,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Six,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Open,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Close,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.One,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Two,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Three,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Subtraction,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Multiplication,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Dot,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Zero,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Delete,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				Icon(imageVector = Icons.AutoMirrored.Outlined.Backspace, contentDescription = null)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Addition,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}

			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Equal,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				ElementKeyboardButtonSymbol(symbol = it.symbol)
			}
		}
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

