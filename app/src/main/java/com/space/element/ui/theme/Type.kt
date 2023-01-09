package com.space.element.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.space.element.R

val fontFamily = FontFamily(
	Font(R.font.mono),
	Font(R.font.mono_italic, style = FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
	displayMedium = TextStyle(
		fontSize = 48.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold
	),
	headlineLarge = TextStyle(
		fontSize = 32.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold
	),
	headlineMedium = TextStyle(
		fontSize = 28.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold
	),
	headlineSmall = TextStyle(
		fontSize = 24.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold
	),
	titleLarge = TextStyle(
		fontSize = 24.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold
	),
	titleMedium = TextStyle(
		fontSize = 20.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold
	),
	titleSmall = TextStyle(
		fontSize = 16.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold
	),
	bodyLarge = TextStyle(
		fontSize = 16.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal
	),
	bodyMedium = TextStyle(
		fontSize = 16.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal
	),
	bodySmall = TextStyle(
		fontSize = 12.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal
	),
	labelLarge = TextStyle(
		fontSize = 16.sp,
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold
	)
)
