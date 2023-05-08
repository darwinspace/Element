package com.space.temp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.space.element.presentation.main.component.list.ElementList
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ScreenPreview() {
	ElementTheme {
		Screen()
	}
}

@Composable
fun Screen() {
	Surface {
		ElementList(
			modifier = Modifier.fillMaxSize(),
			elementList = listOf(),
			elementListMode = ElementListMode.Normal,
			onElementListModeChange = {},
			onElementListItemClick = {}
		)
	}
}
