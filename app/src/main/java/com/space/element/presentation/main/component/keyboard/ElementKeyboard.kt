package com.space.element.presentation.main.component.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
			contentGap = 16.dp,
			contentPadding = PaddingValues(16.dp)
		) { throw NotImplementedError() }
	}
}

@Composable
fun ElementKeyboard(
	contentGap: Dp = 16.dp,
	contentPadding: PaddingValues = PaddingValues(16.dp),
	onButtonClick: (KeyboardButton) -> Unit
) {
	Surface {
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
	contentGap: Dp = 16.dp,
	contentPadding: PaddingValues = PaddingValues(16.dp),
	onButtonClick: (KeyboardButton) -> Unit
) {
	Surface {
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
				keyboardButton = KeyboardButton.Multiplication,
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
				keyboardButton = KeyboardButton.Dot,
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
				keyboardButton = KeyboardButton.Zero,
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
				keyboardButton = KeyboardButton.Delete,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick
			) {
				Icon(
					imageVector = Icons.AutoMirrored.Outlined.Backspace,
					contentDescription = null
				)
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
