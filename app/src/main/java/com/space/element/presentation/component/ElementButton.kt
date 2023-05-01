package com.space.element.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ElementButtonPreview() {
	ElementTheme {
		ElementButton(
			text = "Button",
			onClick = {
				throw NotImplementedError()
			}
		)
	}
}

@Composable
fun ElementButton(
	text: String,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	onClick: () -> Unit
) {
	Button(
		modifier = modifier.defaultMinSize(minHeight = 48.dp),
		enabled = enabled,
		shape = MaterialTheme.shapes.small,
		border = BorderStroke(
			width = 2.dp,
			color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f)
		),
		onClick = onClick,
	) {
		Text(text = text)
	}
}
