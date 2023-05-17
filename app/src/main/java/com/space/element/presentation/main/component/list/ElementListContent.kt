package com.space.element.presentation.main.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.space.element.domain.model.Element

@Composable
fun ElementListContent(
	elementList: List<Element>,
	onClick: (Element) -> Unit,
	onLongClick: (Element) -> Unit,
) {
	LazyColumn(
		contentPadding = PaddingValues(24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		elementListItems(elementList, onClick,onLongClick)
	}
}

fun LazyListScope.elementListItems(
	elementList: List<Element>,
	onClick: (Element) -> Unit,
	onLongClick: (Element) -> Unit,
) {
	items(elementList, { it.name }) { element ->
		ElementListItem(
			modifier = Modifier.fillMaxWidth(),
			element = element,
			onLongClick = onLongClick,
			onClick = onClick
		)
	}
}
