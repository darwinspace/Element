package com.shapes.element.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Space(
	val default: Dp = 0.dp,
	val small: Dp = 12.dp,
	val regular: Dp = 24.dp,
	val large: Dp = 32.dp,
	val extraLarge: Dp = 48.dp
)

val MaterialTheme.space: Space
	@Composable
	@ReadOnlyComposable
	get() = LocalSpace.current
