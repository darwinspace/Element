package com.shapes.element.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Space(
	val default: Dp = 0.dp,
	val minimal: Dp = 12.dp,
	val regular: Dp = 24.dp
)

val MaterialTheme.space: Space
	@Composable
	@ReadOnlyComposable
	get() = LocalSpace.current
