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
		expressionCursorPosition = viewModel.expressionCursorPosition,
		expressionResult = viewModel.expressionResult,
		onExpressionSpaceClick = viewModel::onExpressionSpaceClick,
		elementList = elementList,
		elementListMode = viewModel.elementListMode,
		onAddElementList = viewModel::addElement,
		onElementListItemClick = viewModel::onElementListItemClick,
		onElementListModeChange = viewModel::onElementListModeChange,
		onKeyboardButtonLongClick = viewModel::onKeyboardButtonLongClick,
		onKeyboardButtonClick = viewModel::onKeyboardButtonClick
	)
}
