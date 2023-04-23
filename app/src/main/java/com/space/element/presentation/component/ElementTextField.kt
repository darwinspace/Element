package com.space.element.presentation.component

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ElementTextFieldPreview() {
	ElementTheme {
		Surface {
			ElementTextField(value = "Text", onValueChange = {})
		}
	}
}

/** TODO: Custom OutlinedTextField */
@Composable
fun ElementTextField(
	value: String,
	onValueChange: (String) -> Unit
) {
	BasicTextField(value = value, onValueChange = onValueChange)
}
