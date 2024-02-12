package com.space.element.presentation.main.component.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
			onClick = { throw NotImplementedError() },
			onLongClick = { throw NotImplementedError() }
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ElementListItem(
	modifier: Modifier = Modifier,
	element: Element,
	onLongClick: (Element) -> Unit,
	onClick: (Element) -> Unit
) {
	Surface(
		modifier = modifier
			.clip(MaterialTheme.shapes.medium)
			.combinedClickable(
				onLongClick = {
					onLongClick(element)
				},
				onClick = {
					onClick(element)
				}
			),
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.secondaryContainer
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

			Text(
				text = element.value,
				style = MaterialTheme.typography.bodyMedium
			)
		}
	}
}
