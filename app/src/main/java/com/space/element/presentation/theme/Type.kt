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
	Font(R.font.ibm_plex_mono_bold, weight = FontWeight.Bold),
)

val Typography = Typography(
	titleLarge = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 20.sp
	),
	titleMedium = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 16.sp
	),
	titleSmall = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp
	),
	labelLarge = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 14.sp
	)
)
