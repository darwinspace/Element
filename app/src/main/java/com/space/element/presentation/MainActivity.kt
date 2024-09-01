package com.space.element.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.space.element.presentation.main.MainScreen
import com.space.element.presentation.theme.ElementTheme
import com.space.element.util.enforceNavigationBarContrast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		enableEdgeToEdge()
		enforceNavigationBarContrast()
		super.onCreate(savedInstanceState)
		setContent {
			ElementTheme {
				MainScreen()
			}
		}
	}
}
