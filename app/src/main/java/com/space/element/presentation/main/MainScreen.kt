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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.R
import com.space.element.domain.model.ExpressionItem.*
import com.space.element.presentation.main.component.ElementList
import com.space.element.presentation.main.component.ExpressionList
import com.space.element.presentation.main.component.Keyboard
import com.space.element.presentation.main.model.ExpressionResult
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.util.format
import java.util.*

@Composable
fun MainScreen(size: WindowSizeClass) {
	if (size.widthSizeClass == WindowWidthSizeClass.Compact) {
		VerticalMainScreen(size)
	} else {
		HorizontalMainScreen(size)
	}
}

@Composable
fun HorizontalMainScreen(size: WindowSizeClass) {
	Surface(color = MaterialTheme.colorScheme.background) {
		Row(modifier = Modifier.fillMaxSize()) {
			ElementList(
				modifier = Modifier
					.fillMaxHeight()
					.weight(4f)
			)

			VerticalDivider()

			MainApplicationContent(
				modifier = Modifier
					.fillMaxHeight()
					.weight(6f),
				size = size
			)
		}
	}
}

@Composable
private fun VerticalDivider(
	color: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
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
	color: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
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
private fun VerticalMainScreen(size: WindowSizeClass) {
	val bottomSheetPeekHeight = 56.dp
	val bottomSheetHeight = 384.dp

	BottomSheetScaffold(
		sheetContentColor = MaterialTheme.colorScheme.surface,
		sheetContent = {
			ElementListBottomSheet(
				bottomSheetPeekHeight = bottomSheetPeekHeight,
				bottomSheetHeight = bottomSheetHeight
			)
		},
		sheetShape = RectangleShape,
		sheetElevation = 0.dp,
		sheetPeekHeight = bottomSheetPeekHeight
	) { contentPadding ->
		Surface(modifier = Modifier.padding(contentPadding)) {
			MainApplicationContent(
				modifier = Modifier.fillMaxSize(),
				size = size
			)
		}
	}
}

@Composable
private fun ElementListBottomSheet(bottomSheetPeekHeight: Dp, bottomSheetHeight: Dp) {
	Surface {
		Column {
			ElementListPeek(
				modifier = Modifier
					.height(bottomSheetPeekHeight)
					.fillMaxWidth()
			)

			ElementList(
				modifier = Modifier
					.height(bottomSheetHeight)
					.fillMaxWidth()
			)
		}
	}
}

@Composable
private fun ElementListPeek(modifier: Modifier) {
	val color = MaterialTheme.colorScheme.onPrimaryContainer
	val width = 72.dp
	val height = 4.dp

	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center
	) {
		Box(
			modifier = Modifier
				.clip(CircleShape)
				.background(color)
				.size(width, height)
		)
	}
}

@Composable
private fun MainApplicationContent(
	modifier: Modifier = Modifier,
	size: WindowSizeClass
) {
	Column(modifier = modifier) {
		ExpressionData(modifier = Modifier.weight(1f))
		Keyboard()
	}
}

@Composable
private fun ExpressionData(modifier: Modifier = Modifier) {
	val tonalElevation = 3.dp

	Surface(
		modifier = Modifier
			.verticalScroll(rememberScrollState())
			.then(modifier),
		tonalElevation = tonalElevation
	) {
		Column {
			ExpressionList(modifier = Modifier.fillMaxWidth())
			ResultFrame()
		}
	}
}

@Composable
private fun ResultFrame() {
	val viewModel: MainViewModel = viewModel()
	val resultState = viewModel.expressionResult
	if (resultState is ExpressionResultState.Value) {
		when (val result = resultState.result) {
			is ExpressionResult.Value -> {
				ExpressionResult(
					modifier = Modifier
						.fillMaxWidth()
						.horizontalScroll(rememberScrollState()),
					result = result
				)
			}
			is ExpressionResult.ExpressionException -> {
				ExpressionException(result)
			}
		}
	}
}

@Composable
private fun ExpressionException(result: ExpressionResult.ExpressionException) {
	val textId = if (result.exception is ArithmeticException) {
		R.string.exception_arithmetic
	} else {
		R.string.exception_expression
	}

	Surface(
		modifier = Modifier
			.padding(24.dp)
			.fillMaxWidth(),
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.errorContainer
	) {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(24.dp),
			text = stringResource(textId),
			textAlign = TextAlign.End,
			style = MaterialTheme.typography.headlineSmall
		)
	}
}

@Composable
private fun ExpressionResult(modifier: Modifier, result: ExpressionResult.Value) {
	Surface(modifier = modifier) {
		Box(
			modifier = Modifier.padding(32.dp),
			contentAlignment = Alignment.CenterEnd
		) {
			ResultText(result.value)
		}
	}
}

@Composable
fun ResultText(value: Double) {
	Text(
		text = value.format(),
		textAlign = TextAlign.End,
		style = MaterialTheme.typography.displayMedium
	)
}


