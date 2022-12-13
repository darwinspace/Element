package com.shapes.element.presentation.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapes.element.ui.theme.ElementTheme

@Composable
fun AppTest() {
	LazyRow(
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {

	}
}

@Preview
@Composable
fun AppPreview() {
	ElementTheme {
		AppTest()
	}
}

