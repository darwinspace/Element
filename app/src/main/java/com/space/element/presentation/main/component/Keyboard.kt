package com.space.element.presentation.main.component

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.presentation.main.MainViewModel
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.ui.theme.ElementTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun KeyboardPreview() {
	ElementTheme {
		Surface {
			Keyboard()
		}
	}
}

@Composable
fun Keyboard() {
	Surface {
		KeyboardColumn()
	}
}

@Composable
private fun KeyboardColumn() {
	val padding = 20.dp
	val keyboardPadding =
		PaddingValues(start = padding, top = padding, end = padding, bottom = 0.dp)
	val keyboardGap = 20.dp

	Column(
		modifier = Modifier.padding(keyboardPadding),
		verticalArrangement = Arrangement.spacedBy(keyboardGap)
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardGap)
		) {
			KeyboardDeleteOperatorButton()
			KeyboardOperatorButton(KeyboardButton.Open)
			KeyboardOperatorButton(KeyboardButton.Close)
			KeyboardOperatorButton(KeyboardButton.Divide)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardGap)
		) {
			KeyboardNumberButton(KeyboardButton.Seven)
			KeyboardNumberButton(KeyboardButton.Eight)
			KeyboardNumberButton(KeyboardButton.Nine)
			KeyboardOperatorButton(KeyboardButton.Add)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardGap)
		) {
			KeyboardNumberButton(KeyboardButton.Four)
			KeyboardNumberButton(KeyboardButton.Five)
			KeyboardNumberButton(KeyboardButton.Six)
			KeyboardOperatorButton(KeyboardButton.Subtract)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardGap)
		) {
			KeyboardNumberButton(KeyboardButton.One)
			KeyboardNumberButton(KeyboardButton.Two)
			KeyboardNumberButton(KeyboardButton.Three)
			KeyboardOperatorButton(KeyboardButton.Multiply)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(keyboardGap)
		) {
			KeyboardFunctionOperatorButton()
			KeyboardNumberButton(KeyboardButton.Zero)
			KeyboardDotOperatorButton()
			KeyboardEqualOperatorButton()
		}
	}
}

@Composable
private fun RowScope.KeyboardDotOperatorButton() {
	KeyboardButton(
		modifier = Modifier.weight(1f),
		keyboardButton = KeyboardButton.Dot
	)
}

@Composable
private fun RowScope.KeyboardFunctionOperatorButton() {
	KeyboardButton(
		modifier = Modifier.weight(1f),
		keyboardButton = KeyboardButton.Function,
		fontStyle = FontStyle.Italic
	)
}

@Composable
private fun RowScope.KeyboardDeleteOperatorButton() {
	KeyboardButton(
		modifier = Modifier.weight(1f),
		keyboardButton = KeyboardButton.Delete,
		color = MaterialTheme.colorScheme.tertiaryContainer
	)
}

@Composable
private fun RowScope.KeyboardEqualOperatorButton() {
	KeyboardButton(
		modifier = Modifier.weight(1f),
		keyboardButton = KeyboardButton.Equal,
		color = MaterialTheme.colorScheme.primary
	)
}

@Composable
private fun RowScope.KeyboardOperatorButton(keyboardButton: KeyboardButton) {
	KeyboardButton(
		modifier = Modifier.weight(1f),
		keyboardButton = keyboardButton,
		color = MaterialTheme.colorScheme.secondaryContainer
	)
}

@Composable
private fun RowScope.KeyboardNumberButton(button: KeyboardButton) {
	KeyboardButton(
		modifier = Modifier.weight(1f),
		keyboardButton = button
	)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun KeyboardButton(
	modifier: Modifier = Modifier,
	keyboardButton: KeyboardButton,
	fontStyle: FontStyle = FontStyle.Normal,
	color: Color = MaterialTheme.colorScheme.surface
) {
	val viewModel = viewModel<MainViewModel>()
	val shape = MaterialTheme.shapes.medium
	val tonalElevation = 1.dp

	Surface(
		modifier = modifier
			.clip(shape)
			.combinedClickable(
				role = Role.Button,
				onLongClick = {
					TODO()
				},
				onClick = {
					viewModel.onKeyboardButtonClick(keyboardButton)
				}
			),
		shape = shape,
		color = color,
		tonalElevation = tonalElevation
	) {
		Box(
			modifier = Modifier
				.height(48.dp)
				.defaultMinSize(
					minWidth = 40.dp,
					minHeight = 40.dp
				),
			contentAlignment = Alignment.Center
		) {
			KeyboardButtonText(keyboardButton.symbol, fontStyle)
		}
	}
}

@Composable
private fun KeyboardButtonText(text: String, fontStyle: FontStyle) {
	Text(
		text = text,
		fontStyle = fontStyle,
		style = MaterialTheme.typography.labelLarge
	)
}
