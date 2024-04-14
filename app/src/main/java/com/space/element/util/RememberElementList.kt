package com.space.element.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.space.element.domain.model.Element
import com.space.element.domain.model.ElementListItem
import com.space.element.presentation.main.model.LibraryState

/** TODO: Use remember with listSaver. */
@Composable
fun rememberElementList(
	list: List<Element>,
	mode: LibraryState
): SnapshotStateList<ElementListItem> {
	return remember(list, mode) {
		list.map {
			ElementListItem(element = it, selected = false)
		}.toMutableStateList()
	}
}
