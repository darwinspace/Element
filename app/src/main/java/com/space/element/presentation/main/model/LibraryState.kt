package com.space.element.presentation.main.model

sealed interface LibraryState {
	data object Normal : LibraryState
	data object Create : LibraryState
	data object Search : LibraryState
	data object Edit : LibraryState
	data object Function : LibraryState
}
