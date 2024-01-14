package com.space.element.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
	background = Color.White,
	onBackground = Color.Black,
)

private val DarkColorScheme = darkColorScheme(
	background = Color.Black,
	onBackground = Color.White
)

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
			val elevation = if(darkTheme) 3.dp else 12.dp
			val color = colorScheme.surfaceColorAtElevation(elevation)
			val window = (view.context as Activity).window
			window.statusBarColor = color.toArgb()
			val windowInsetsController = WindowCompat.getInsetsController(window, view)
			windowInsetsController.isAppearanceLightStatusBars = !darkTheme
		}
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}
