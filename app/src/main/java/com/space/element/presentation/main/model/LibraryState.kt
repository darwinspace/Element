package com.space.element.presentation.main.model

sealed interface LibraryState {
	data object ElementList : LibraryState
	data object CreateElement : LibraryState
	data object SearchElement : LibraryState
	data object RemoveElement : LibraryState
	data object FunctionList : LibraryState
	data object CreateFunction : LibraryState
}
