package com.space.element.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

data class Elevation(
	val unspecified: Dp = Dp.Unspecified,
	val LevelZero: Dp = 0.dp,
	val LevelOne: Dp = 1.dp,
	val LevelTwo: Dp = 2.dp,
	val LevelThree: Dp = 3.dp,
	val LevelFour: Dp = 4.dp,
	val LevelFive: Dp = 5.dp,
)

val LocalSpace = staticCompositionLocalOf { Space() }
val LocalTonalElevation = staticCompositionLocalOf { Elevation() }

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
			val windowInsetsController = WindowCompat.getInsetsController(window, view)
			windowInsetsController.isAppearanceLightStatusBars = darkTheme
		}
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}
