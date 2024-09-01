package com.space.element.presentation.main.model

sealed interface LibraryState {
	sealed interface ElementState : LibraryState {
		data object List : ElementState
		data object Create : ElementState
		data object Search : ElementState
		data object Edit : ElementState
	}

	sealed interface FunctionState : LibraryState {
		data object List : FunctionState
		data object Create : FunctionState
		data object Edit : FunctionState
	}
}
