package com.space.element.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.presentation.main.MainScreen
import com.space.element.presentation.main.MainViewModel
import com.space.element.presentation.theme.ElementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val viewModel = viewModel<MainViewModel>()
			val expression  = viewModel.expression
			val expressionCursor = viewModel.expressionCursor
			val expressionResult = viewModel.expressionResult
			ElementTheme {
				MainScreen()
			}
		}
	}
}
