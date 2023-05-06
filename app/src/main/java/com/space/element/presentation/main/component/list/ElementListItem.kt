package com.space.element.presentation.main.component.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
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
	val (name, value) = element

	val space = 24.dp
	val padding = PaddingValues(20.dp)
	val textStyle = MaterialTheme.typography.titleSmall
	val shape = MaterialTheme.shapes.medium
	val tonalElevation = 6.dp

	val borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
	val border = BorderStroke(width = 2.dp, color = borderColor)

	Surface(
		modifier = modifier
			.clip(shape)
			.clickable(role = Role.Button, onClick = onClick),
		shape = shape,
		tonalElevation = tonalElevation,
		border = border
	) {
		Row(
			modifier = Modifier.padding(padding),
			horizontalArrangement = Arrangement.spacedBy(space),
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				modifier = Modifier.weight(1f),
				text = name,
				textAlign = TextAlign.Justify,
				style = textStyle
			)
			Text(text = value, style = textStyle)
		}
	}
}
