package com.space.element.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.space.element.presentation.theme.ElementTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Preview
@Composable
fun ElementTextFieldPreview() {
	var text by remember { mutableStateOf(String()) }
	ElementTheme {
		ElementTextField(
			value = text,
			onValueChange = { text = it },
			placeholder = {
				Text(
					text = "Placeholder",
					style = MaterialTheme.typography.bodyMedium
				)
			}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementTextField(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
	singleLine: Boolean = true,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	placeholder: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	shape: Shape = MaterialTheme.shapes.small,
	colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
	BasicTextField(
		value = value,
		onValueChange = onValueChange,
		interactionSource = interactionSource,
		modifier = modifier,
		textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onSurface),
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
				trailingIcon = trailingIcon,
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
