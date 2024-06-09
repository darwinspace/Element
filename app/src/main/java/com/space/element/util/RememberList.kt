package com.space.element.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.space.element.domain.model.Element
import com.space.element.domain.model.ElementListItem
import com.space.element.domain.model.Function
import com.space.element.domain.model.FunctionListItem
import com.space.element.presentation.main.model.LibraryState

/** TODO: Use remember with listSaver. */
@Composable
fun rememberElementList(
	list: List<Element>,
	state: LibraryState
): SnapshotStateList<ElementListItem> {
	return remember(list, state) {
		list.map {
			ElementListItem(element = it, selected = false)
		}.toMutableStateList()
	}
}

/** TODO: Use remember with listSaver. */
@Composable
fun rememberFunctionList(
	list: List<Function>,
	state: LibraryState
): SnapshotStateList<FunctionListItem> {
	return remember(list, state) {
		list.map {
			FunctionListItem(function = it, selected = false)
		}.toMutableStateList()
	}
}

