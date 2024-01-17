package com.space.element.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.space.element.R

val fontFamily = FontFamily(
	Font(R.font.ibm_plex_mono),
	Font(R.font.ibm_plex_mono_medium, weight = FontWeight.Medium),
	Font(R.font.ibm_plex_mono_bold, weight = FontWeight.Bold),
)

val Typography = Typography(
	displaySmall = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 40.sp
	),
	headlineLarge = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 32.sp
	),
	headlineMedium = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 28.sp,
		letterSpacing = 2.sp
	),
	headlineSmall = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 24.sp
	),
	titleLarge = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 24.sp
	),
	titleMedium = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 20.sp
	),
	titleSmall = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 16.sp
	),
	bodyLarge = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 20.sp
	),
	bodyMedium = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp
	),
	bodySmall = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp
	),
	labelLarge = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 14.sp
	)
)
