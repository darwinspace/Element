package com.space.element.presentation.main.model

sealed class ElementListState {
	object IdleState : ElementListState()
	object AddState : ElementListState()
	object SearchState : ElementListState()
}
