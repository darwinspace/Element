package com.shapes.element.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
	primary = Purple200,
	primaryVariant = Purple700,
	secondary = Teal200
)

private val LightColorPalette = lightColors(
	primary = Purple500,
	primaryVariant = Purple700,
	secondary = Teal200
)

val Colors.elementBorder: Color
	get() {
		return onSurface.copy(alpha = 0.12f)
	}

@Composable
fun ElementTheme(content: @Composable () -> Unit) {
	val darkTheme: Boolean = isSystemInDarkTheme()

	val colors = if (darkTheme) {
		DarkColorPalette
	} else {
		LightColorPalette
	}

	MaterialTheme(
		colors = colors,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}