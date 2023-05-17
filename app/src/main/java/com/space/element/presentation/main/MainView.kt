package com.space.element.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainView() {
	val viewModel = viewModel<MainViewModel>()

	val elementList by viewModel.elementList.collectAsState()

	MainScreen(
		expression = viewModel.expression,
		expressionResult = viewModel.expressionResult,
		expressionCursorPosition = viewModel.expressionCursorPosition,
		onExpressionCursorPositionChange = viewModel::onExpressionCursorPositionChange,
		elementList = elementList,
		onElementListItemClick = viewModel::onElementListItemClick,
		onElementListItemLongClick = viewModel::onElementListItemLongClick,
		elementListMode = viewModel.elementListMode,
		onElementListModeChange = viewModel::onElementListModeChange,
		searchValue = viewModel.searchValue,
		onSearchValueChange = viewModel::onSearchValueChange,
		elementName = viewModel.elementName,
		onElementNameChange = viewModel::onElementNameChange,
		elementValue = viewModel.elementValue,
		onElementValueChange = viewModel::onElementValueChange,
		createElementEnabled = viewModel.isCreateElementEnabled(),
		onCreateElementClick = viewModel::onCreateElementClick,
		onKeyboardButtonClick = viewModel::onKeyboardButtonClick
	)
}
