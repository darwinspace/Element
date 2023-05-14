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
import androidx.compose.ui.tooling.preview.Wallpapers
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

private fun getPreviewExpressionList(): List<ExpressionItem> {
	return listOf(
		NumberItem(number = '9'),
		OperatorItem(Operator.Addition),
		NumberItem(number = '2'),
		OperatorItem(Operator.Multiplication),
		ElementItem(element = Element(name = "Taco 🌮", value = "20"))
	)
}

//@Preview(
//	uiMode = Configuration.UI_MODE_NIGHT_YES,
//	device = "spec:width=411dp,height=891dp,orientation=landscape"
//)
//@Preview(
//	uiMode = Configuration.UI_MODE_NIGHT_YES,
//	device = "spec:width=1280dp,height=800dp,dpi=480"
//)
//@Preview(
//	uiMode = Configuration.UI_MODE_NIGHT_YES,
//	device = "spec:width=1920dp,height=1080dp,dpi=480"
//)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO,
	device = "spec:width=411dp,height=891dp",
	wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE
)
@Composable
fun MainScreenPreview() {
	val expression = remember { getPreviewExpressionList().toMutableStateList() }
	var expressionCursorPosition by remember { mutableStateOf(0) }

	val elementList = remember { mutableStateListOf<Element>() }
	var elementListMode by remember { mutableStateOf<ElementListMode>(ElementListMode.Normal) }

	ElementTheme {
		MainScreen(
			expression = expression,
			expressionResult = ExpressionResultState.Value(value = 10.0),
			expressionCursorPosition = expressionCursorPosition,
			onExpressionCursorPositionChange = { expressionCursorPosition = it },
			elementList = elementList,
			elementListMode = elementListMode,
			onAddElementList = { name, value -> elementList.add(Element(name, value)) },
			onElementListModeChange = { elementListMode = it },
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
	onExpressionCursorPositionChange: (Int) -> Unit,
	elementList: List<Element>,
	elementListMode: ElementListMode,
	onAddElementList: (String, String) -> Unit,
	onElementListModeChange: (ElementListMode) -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit,
	onElementListItemClick: (Element) -> Unit
) {
	ColumnMainScreen(
		expression = expression,
		expressionResult = expressionResult,
		expressionCursorPosition = expressionCursorPosition,
		onExpressionCursorPositionChange = onExpressionCursorPositionChange,
		elementList = elementList,
		elementListMode = elementListMode,
		onAddElementList = onAddElementList,
		onElementListItemClick = onElementListItemClick,
		onElementListModeChange = onElementListModeChange,
		onKeyboardButtonClick = onKeyboardButtonClick
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnMainScreen(
	expression: List<ExpressionItem>,
	expressionCursorPosition: Int,
	expressionResult: ExpressionResultState,
	onExpressionCursorPositionChange: (Int) -> Unit,
	elementList: List<Element>,
	elementListMode: ElementListMode,
	onAddElementList: (String, String) -> Unit,
	onElementListItemClick: (Element) -> Unit,
	onElementListModeChange: (ElementListMode) -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit
) {
	BottomSheetScaffold(
		sheetContent = {
			ElementListBottomSheetContent(
				elementList = elementList,
				elementListMode = elementListMode,
				onAddElementList = onAddElementList,
				onElementListModeChange = onElementListModeChange,
				onElementListItemClick = onElementListItemClick
			)
		}
	) { contentPadding ->
		MainContent(
			modifier = Modifier
				.fillMaxSize()
				.padding(contentPadding),
			expression = expression,
			expressionResult = expressionResult,
			expressionCursorPosition = expressionCursorPosition,
			onExpressionCursorPositionChange = onExpressionCursorPositionChange,
			onKeyboardButtonClick = onKeyboardButtonClick
		)
	}
}

@Composable
private fun ElementListBottomSheetContent(
	elementList: List<Element>,
	elementListMode: ElementListMode,
	onAddElementList: (String, String) -> Unit,
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
		onAddElementList = onAddElementList,
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
	onExpressionCursorPositionChange: (Int) -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit
) {
	Column(modifier = modifier) {
		ElementHeader(
			modifier = Modifier
				.weight(1f)
				.verticalScroll(rememberScrollState()),
			expression = expression,
			expressionResultState = expressionResult,
			expressionCursorPosition = expressionCursorPosition,
			onExpressionCursorPositionChange = onExpressionCursorPositionChange
		)

		ElementKeyboard(
			contentGap = 16.dp,
			contentPadding = PaddingValues(16.dp),
			onButtonClick = onKeyboardButtonClick
		)
	}
}
