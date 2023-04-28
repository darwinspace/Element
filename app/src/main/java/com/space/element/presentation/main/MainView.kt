package com.space.element.presentation.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainView() {
	val viewModel = viewModel<MainViewModel>()
	val expression  = viewModel.expression
	val expressionCursor = viewModel.expressionCursor
	val expressionResult = viewModel.expressionResult

	MainScreen()
}
