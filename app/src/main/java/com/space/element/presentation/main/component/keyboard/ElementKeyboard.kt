package com.space.element.presentation.main.component.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
			contentPadding = PaddingValues(16.dp),
			onButtonLongClick = {
				throw NotImplementedError()
			},
			onButtonClick = {
				throw NotImplementedError()
			}
		)
	}
}

@Composable
fun ElementKeyboard(
	contentGap: Dp,
	contentPadding: PaddingValues,
	onButtonLongClick: (KeyboardButton) -> Unit,
	onButtonClick: (KeyboardButton) -> Unit
) {
	Surface {
		ElementKeyboardContent(
			keyboardContentPadding = contentPadding,
			keyboardContentGap = contentGap,
			onButtonLongClick = onButtonLongClick,
			onButtonClick = onButtonClick
		)
	}
}

@Composable
private fun ElementKeyboardContent(
	keyboardContentPadding: PaddingValues,
	keyboardContentGap: Dp,
	onButtonLongClick: (KeyboardButton) -> Unit,
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
				keyboardButton = KeyboardButton.Delete,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick
			) {
				Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
			}
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Open,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Close,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Division,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Seven,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Eight,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Nine,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Addition,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					Icon(imageVector = Icons.Default.Add, contentDescription = null)
				}
			)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Four,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Five,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Six,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Subtraction,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.One,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Two,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Three,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Multiplication,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardContentGap)
		) {
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Function,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Zero,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Dot,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
			ElementKeyboardButton(
				keyboardButton = KeyboardButton.Equal,
				modifier = Modifier.weight(1f),
				onClick = onButtonClick,
				onLongClick = onButtonLongClick,
				content = {
					ElementKeyboardButtonText(text = it.symbol)
				}
			)
		}
	}
}
