package com.space.element.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainView() {
	val viewModel = viewModel<MainViewModel>()

	val elementList by viewModel.elementList.collectAsState()

	val elementListQuery by viewModel.elementListQuery.collectAsState()

	val elementListMode by viewModel.elementListMode.collectAsState()

	val elementName by viewModel.elementName.collectAsState()

	val elementValue by viewModel.elementValue.collectAsState()

	val createElementEnabled by viewModel.createButtonEnabled.collectAsState()

	MainScreen(
		expression = viewModel.expression,
		expressionResult = viewModel.expressionResult,
		expressionCursorPosition = viewModel.expressionCursorPosition,
		onExpressionCursorPositionChange = viewModel::onExpressionCursorPositionChange,
		elementList = elementList,
		onElementListItemClick = viewModel::onElementListItemClick,
		onElementListItemLongClick = viewModel::onElementListItemLongClick,
		elementListMode = elementListMode,
		onElementListModeChange = viewModel::onElementListModeChange,
		elementListQuery = elementListQuery,
		onElementListQueryChange = viewModel::onElementListQueryChange,
		elementName = elementName,
		onElementNameChange = viewModel::onElementNameChange,
		elementValue = elementValue,
		onElementValueChange = viewModel::onElementValueChange,
		createElementEnabled = createElementEnabled,
		onCreateElementClick = viewModel::onCreateElementClick,
		onKeyboardButtonClick = viewModel::onKeyboardButtonClick
	)
}
