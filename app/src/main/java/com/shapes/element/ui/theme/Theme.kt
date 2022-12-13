package com.shapes.element.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

val LocalSpace = compositionLocalOf { Space() }

@Composable
fun ElementTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {
	val colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
		val context = LocalContext.current
		if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
	} else {
		if (darkTheme) DarkColorScheme else LightColorScheme
	}

	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			val window = (view.context as Activity).window
			window.statusBarColor = colorScheme.primary.toArgb()
			WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
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
