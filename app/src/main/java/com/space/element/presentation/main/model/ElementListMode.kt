package com.space.element.presentation.main.model

sealed class ElementListMode {
	object Normal : ElementListMode()
	object Create : ElementListMode()
	object Search : ElementListMode()
}
