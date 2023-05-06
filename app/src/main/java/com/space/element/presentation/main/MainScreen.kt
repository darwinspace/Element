package com.space.element.presentation.main

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.space.element.domain.model.Element
import com.space.element.domain.model.ExpressionItem
import com.space.element.domain.model.ExpressionItem.*
import com.space.element.presentation.main.component.ElementHeader
import com.space.element.presentation.main.component.keyboard.ElementKeyboard
import com.space.element.presentation.main.component.list.ElementList
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.Operator
import com.space.element.presentation.theme.ElementTheme
import java.util.*

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
	var elementListMode by remember { mutableStateOf<ElementListMode>(ElementListMode.Normal) }

	ElementTheme {
		MainScreen(
			expression = listOf(
				NumberItem(number = "9"),
				OperatorItem(Operator.Addition),
				NumberItem(number = "1"),
			),
			expressionCursorPosition = 0,
			expressionResult = ExpressionResultState.Value(value = 10.0),
			onExpressionSpaceClick = { throw NotImplementedError() },
			elementList = emptyList(),
			elementListMode = elementListMode,
			onElementListModeChange = { elementListMode = it },
			onKeyboardButtonLongClick = { throw NotImplementedError() },
			onKeyboardButtonClick = { throw NotImplementedError() },
			onElementListItemClick = { throw NotImplementedError() }
		)
	}
}

// TODO: BoxWithConstraints.
@Composable
fun MainScreen(
	expression: List<ExpressionItem>,
	expressionCursorPosition: Int,
	expressionResult: ExpressionResultState,
	onExpressionSpaceClick: (Int) -> Unit,
	elementList: List<Element>,
	elementListMode: ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit,
	onKeyboardButtonLongClick: (KeyboardButton) -> Unit,
	onElementListItemClick: (Element) -> Unit
) {
	ColumnMainScreen(
		expression = expression,
		expressionCursorPosition = expressionCursorPosition,
		expressionResult = expressionResult,
		elementList = elementList,
		elementListMode = elementListMode,
		onElementListModeChange = onElementListModeChange,
		onExpressionSpaceClick = onExpressionSpaceClick,
		onKeyboardButtonClick = onKeyboardButtonClick,
		onKeyboardButtonLongClick = onKeyboardButtonLongClick,
		onElementListItemClick = onElementListItemClick
	)
}

//@Composable
//fun RowMainScreen(
//	expression: List<ExpressionItem>,
//	expressionCursorPosition: Int,
//	expressionResult: ExpressionResultState
//) {
//	Surface(color = MaterialTheme.colorScheme.background) {
//		Row(modifier = Modifier.fillMaxSize()) {
//			ElementList(
//				modifier = Modifier
//					.fillMaxHeight()
//					.weight(4f),
//				elementList = emptyList(),
//				ElementListMode.Normal
//			)
//
//			VerticalDivider()
//
//			MainContent(
//				modifier = Modifier
//					.fillMaxHeight()
//					.weight(6f),
//				expression = expression,
//				expressionCursorPosition = expressionCursorPosition,
//				expressionResult = expressionResult
//			)
//		}
//	}
//}

//@Composable
//private fun VerticalDivider(
//	color: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
//	thickness: Dp = 2.dp
//) {
//	Box(
//		modifier = Modifier
//			.fillMaxHeight()
//			.width(thickness)
//			.background(color = color)
//	)
//}

@Composable
private fun HorizontalDivider(
	color: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp),
	thickness: Dp = 2.dp
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(thickness)
			.background(color = color)
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnMainScreen(
	expression: List<ExpressionItem>,
	expressionCursorPosition: Int,
	expressionResult: ExpressionResultState,
	onExpressionSpaceClick: (Int) -> Unit,
	elementList: List<Element>,
	elementListMode: ElementListMode,
	onElementListItemClick: (Element) -> Unit,
	onElementListModeChange: (ElementListMode) -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit,
	onKeyboardButtonLongClick: (KeyboardButton) -> Unit,
) {
	BottomSheetScaffold(
		sheetContent = {
			ElementListBottomSheetContent(
				elementList = elementList,
				elementListMode = elementListMode,
				onElementListModeChange = onElementListModeChange,
				onElementListItemClick = onElementListItemClick
			)
		},
		sheetShape = RectangleShape,
		sheetTonalElevation = 0.dp
	) { contentPadding ->
		MainContent(
			modifier = Modifier
				.fillMaxSize()
				.padding(contentPadding),
			expression = expression,
			expressionCursorPosition = expressionCursorPosition,
			expressionResult = expressionResult,
			onExpressionSpaceClick = onExpressionSpaceClick,
			onKeyboardButtonClick = onKeyboardButtonClick,
			onKeyboardButtonLongClick = onKeyboardButtonLongClick
		)
	}
}

@Composable
private fun ElementListBottomSheetContent(
	elementList: List<Element>,
	elementListMode: ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	onElementListItemClick: (Element) -> Unit,
) {
	val bottomSheetHeight = 512.dp

	ElementList(
		modifier = Modifier
			.height(bottomSheetHeight)
			.fillMaxWidth(),
		elementList = elementList,
		elementListMode = elementListMode,
		onElementListModeChange = onElementListModeChange,
		onElementListItemClick = onElementListItemClick
	)
}

@Composable
private fun MainContent(
	modifier: Modifier = Modifier,
	expression: List<ExpressionItem>,
	expressionCursorPosition: Int,
	expressionResult: ExpressionResultState,
	onExpressionSpaceClick: (Int) -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit,
	onKeyboardButtonLongClick: (KeyboardButton) -> Unit
) {
	Column(modifier = modifier) {
		ElementHeader(
			modifier = Modifier
				.weight(1f)
				.verticalScroll(rememberScrollState()),
			expression = expression,
			expressionCursorPosition = expressionCursorPosition,
			expressionResultState = expressionResult,
			onExpressionSpaceClick = onExpressionSpaceClick
		)

		HorizontalDivider()

		ElementKeyboard(
			contentGap = 16.dp,
			contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
			onButtonLongClick = onKeyboardButtonLongClick,
			onButtonClick = onKeyboardButtonClick
		)
	}
}
