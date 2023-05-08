package com.space.element.presentation.main.component.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.space.element.R

@Composable
fun getText(): AnnotatedString {
	return buildAnnotatedString {
		append(text = stringResource(R.string.empty_element_list_content_action))
		withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
			append(text = " \"")
			append(text = stringResource(R.string.button_add_element))
			append(text = "\" ")
		}
		append(text = stringResource(R.string.empty_element_list_content_reason))
	}
}

@Composable
fun EmptyElementListCard() {
	Surface(
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.primaryContainer
	) {
		Text(
			text = getText(),
			modifier = Modifier
				.fillMaxWidth()
				.padding(32.dp),
			style = MaterialTheme.typography.bodyMedium,
			textAlign = TextAlign.Center
		)
	}
}
