package com.space.element.presentation.main.model

sealed interface ElementListMode {
	data object Normal : ElementListMode
	data object Create : ElementListMode
	data object Search : ElementListMode
	data object Edit : ElementListMode
	data object Function : ElementListMode
}
