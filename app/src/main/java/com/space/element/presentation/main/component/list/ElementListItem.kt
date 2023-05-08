package com.space.element.presentation.main.component.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.space.element.domain.model.Element
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ElementListItemPreview() {
	ElementTheme {
		ElementListItem(
			element = Element(name = "Item", value = "10"),
			onClick = {
				throw NotImplementedError()
			}
		)
	}
}

@Composable
fun ElementListItem(
	modifier: Modifier = Modifier,
	element: Element,
	onClick: () -> Unit
) {
	Surface(
		modifier = modifier,
		shape = MaterialTheme.shapes.medium,
		tonalElevation = 6.dp,
		border = BorderStroke(
			width = 2.dp,
			color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
		),
		onClick = onClick
	) {
		Row(
			modifier = Modifier.padding(20.dp),
			horizontalArrangement = Arrangement.spacedBy(24.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				modifier = Modifier.weight(1f),
				text = element.name,
				textAlign = TextAlign.Justify,
				style = MaterialTheme.typography.titleSmall
			)
			Text(text = element.value, style = MaterialTheme.typography.titleSmall)
		}
	}
}
