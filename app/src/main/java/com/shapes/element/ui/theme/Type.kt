package com.shapes.element.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shapes.element.R

val fontFamily = FontFamily(
	Font(R.font.mono),
	Font(R.font.mono_medium, FontWeight.Medium),
	Font(R.font.mono_bold, FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
	body1 = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp
	), button = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 16.sp
	), caption = TextStyle(
		fontFamily = fontFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp
	)
)
