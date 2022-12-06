package com.shapes.element.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

val LocalSpace = compositionLocalOf { Space() }

@Composable
fun ElementTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {
	val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

	val colorScheme = if (dynamicColor) {
		if (darkTheme) {
			dynamicDarkColorScheme(LocalContext.current)
		} else {
			dynamicLightColorScheme(LocalContext.current)
		}
	} else {
		if (darkTheme) {
			DarkColorScheme
		} else {
			LightColorScheme
		}
	}

	CompositionLocalProvider(LocalSpace provides Space()) {
		MaterialTheme(
			colorScheme = colorScheme,
			typography = Typography,
			shapes = Shapes,
			content = content
		)
	}
}
