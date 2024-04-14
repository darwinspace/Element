package com.space.element.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.space.element.domain.model.Function
import com.space.element.domain.model.Operator

@Composable
fun rememberFunctionName(function: Function): AnnotatedString {
	return remember(function) {
		buildAnnotatedString {
			withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
				append(function.name)
			}

			withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
				append(Operator.Open.symbol)
			}

			withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
				append("x")
			}

			withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
				append(Operator.Close.symbol)
			}
		}
	}
}
