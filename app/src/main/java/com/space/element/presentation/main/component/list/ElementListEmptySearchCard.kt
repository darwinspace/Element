package com.space.element.presentation.main.component.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.space.element.R

@Composable
fun EmptySearchElementListCard() {
	val space = 24.dp
	Surface(
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.primaryContainer
	) {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(space),
			text = stringResource(R.string.empty_element_search_list),
			fontStyle = FontStyle.Italic,
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center
		)
	}
}
