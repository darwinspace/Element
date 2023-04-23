package com.space.element.presentation.main

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.space.element.domain.model.ExpressionItem.*
import com.space.element.presentation.main.component.ElementHeader
import com.space.element.presentation.main.component.keyboard.ElementKeyboard
import com.space.element.presentation.main.component.list.ElementList
import com.space.element.presentation.main.model.ElementListState
import com.space.element.presentation.theme.ElementTheme
import java.util.*

@Preview
@Composable
fun MainScreenPreview() {
	ElementTheme {
		MainScreen()
	}
}

// TODO: BoxWithConstraints.
@Composable
fun MainScreen() {
	ColumnMainScreen()
}

@Composable
fun RowMainScreen() {
	Surface(color = MaterialTheme.colorScheme.background) {
		Row(modifier = Modifier.fillMaxSize()) {
			ElementList(
				modifier = Modifier
					.fillMaxHeight()
					.weight(4f),
				elements = emptyList(),
				ElementListState.IdleState
			)

			VerticalDivider()

			MainContent(
				modifier = Modifier
					.fillMaxHeight()
					.weight(6f)
			)
		}
	}
}

@Composable
private fun VerticalDivider(
	color: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
	thickness: Dp = 2.dp
) {
	Box(
		modifier = Modifier
			.fillMaxHeight()
			.width(thickness)
			.background(color = color)
	)
}

@Composable
private fun HorizontalDivider(
	color: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
	thickness: Dp = 2.dp
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(thickness)
			.background(color = color)
	)
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun ColumnMainScreen() {
	val bottomSheetPeekHeight = 52.dp
	val bottomSheetHeight = 512.dp

	BottomSheetScaffold(
		sheetContent = {
			Surface {
				Column {
					ElementListBottomSheet(bottomSheetHeight)
				}
			}
		},
		sheetShape = RectangleShape,
		sheetElevation = 0.dp,
		sheetPeekHeight = bottomSheetPeekHeight,
		backgroundColor = MaterialTheme.colorScheme.surface
	) { contentPadding ->
		MainContent(
			modifier = Modifier
				.fillMaxSize()
				.padding(contentPadding)
		)
	}
}

@Composable
private fun ElementListBottomSheet(bottomSheetHeight: Dp) {
	ElementListPeek(
		modifier = Modifier
			.height(28.dp)
			.fillMaxWidth()
	)

	ElementList(
		modifier = Modifier
			.height(bottomSheetHeight)
			.fillMaxWidth(),
		elements = emptyList(),
		ElementListState.IdleState
	)
}

@Composable
private fun ElementListPeek(modifier: Modifier) {
	val color = MaterialTheme.colorScheme.onPrimaryContainer
	val width = 72.dp
	val height = 4.dp

	Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
		Box(
			modifier = Modifier
				.padding(top = 16.dp)
				.clip(CircleShape)
				.background(color)
				.size(width, height)
		)
	}
}

@Composable
private fun MainContent(modifier: Modifier = Modifier) {
	Column(modifier = modifier) {
		ElementHeader(
			modifier = Modifier
				.weight(1f)
				.verticalScroll(rememberScrollState())
		)
		HorizontalDivider()
		ElementKeyboard(
			contentGap = 16.dp,
			contentPadding = PaddingValues(16.dp),
			onLongClick = {
				throw NotImplementedError()
			},
			onClick = {
				throw NotImplementedError()
			}
		)
	}
}
