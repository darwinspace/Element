package com.space.element.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ElementTextFieldPreview() {
	ElementTheme {
		Surface {
			ElementTextField(
				value = "",
				onValueChange = {},
				placeholder = {
					Text("Placeholder")
				}
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementTextField(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
	singleLine: Boolean = true,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	placeholder: @Composable (() -> Unit)? = null,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	shape: Shape = MaterialTheme.shapes.small,
	colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
	BasicTextField(
		value = value,
		onValueChange = onValueChange,
		interactionSource = interactionSource,
		modifier = modifier,
		textStyle = textStyle,
		singleLine = singleLine,
		keyboardOptions = keyboardOptions,
		cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
		decorationBox = @Composable {
			OutlinedTextFieldDefaults.DecorationBox(
				value = value,
				innerTextField = it,
				enabled = enabled,
				singleLine = singleLine,
				visualTransformation = VisualTransformation.None,
				interactionSource = interactionSource,
				placeholder = placeholder,
				colors = colors,
				contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
				container = {
					OutlinedTextFieldDefaults.ContainerBox(
						enabled = enabled,
						isError = false,
						interactionSource = interactionSource,
						colors = colors,
						shape = shape
					)
				}
			)
		}
	)
}
