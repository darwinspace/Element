package com.space.element.presentation.main.component.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementListItem(
	modifier: Modifier = Modifier,
	element: Element,
	onLongClick: (Element) -> Unit,
	onClick: (Element) -> Unit
) {
	val state = rememberDismissState()
	SwipeToDismiss(
		state = state,
		background = {
			Surface(
				modifier = Modifier.fillMaxSize(),
				shape = MaterialTheme.shapes.medium,
				color = MaterialTheme.colorScheme.error
			) {
				Box {
					Icon(
						modifier = Modifier
							.padding(16.dp)
							.align(Alignment.CenterEnd),
						imageVector = Icons.Default.Close,
						contentDescription = null
					)
				}
			}
		},
		dismissContent = {
			Surface(
				modifier = modifier,
				shape = MaterialTheme.shapes.medium,
				tonalElevation = 6.dp,
				onClick = {
					onClick(element)
				}
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
		},
		directions = setOf(DismissDirection.EndToStart)
	)
}
