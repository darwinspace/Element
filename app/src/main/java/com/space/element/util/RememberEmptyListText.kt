package com.space.element.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun rememberEmptyListText(
	action: String,
	button: String,
	reason: String
): AnnotatedString {
	return remember {
		buildAnnotatedString {
			append(text = action)
			withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
				append(text = " \"")
				append(text = button)
				append(text = "\" ")
			}
			append(text = reason)
		}
	}
}
