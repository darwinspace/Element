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

private fun getPreviewElementList(): List<Element> {
	return buildList {
		repeat(10) {
			val element = Element("Item $it", it.toString())
			add(element)
		}
	}
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

	val elementList = remember { getPreviewElementList().toMutableStateList() }
	var elementListMode by remember { mutableStateOf<ElementListMode>(ElementListMode.Normal) }

	ElementTheme {
		MainScreen(
			expression = expression,
			expressionResult = ExpressionResultState.Value(value = 10.0),
			expressionCursorPosition = expressionCursorPosition,
			onExpressionCursorPositionChange = { expressionCursorPosition = it },
			elementList = elementList,
			onElementListItemClick = { throw NotImplementedError() },
			onElementListItemLongClick = { elementList.remove(it) },
			elementListMode = elementListMode,
			onElementListModeChange = { elementListMode = it },
			elementListQuery = String(),
			onElementListQueryChange = { throw NotImplementedError() },
			elementName = String(),
			onElementNameChange = { throw NotImplementedError() },
			elementValue = String(),
			onElementValueChange = { throw NotImplementedError() },
			createElementEnabled = true,
			onCreateElementClick = { throw NotImplementedError() },
			onKeyboardButtonClick = { throw NotImplementedError() }
		)
	}
}

// TODO: BoxWithConstraints.
@Composable
fun MainScreen(
	expression: List<ExpressionItem>,
	expressionResult: ExpressionResultState,
	expressionCursorPosition: Int,
	onExpressionCursorPositionChange: (Int) -> Unit,
	elementList: List<Element>,
	onElementListItemClick: (Element) -> Unit,
	onElementListItemLongClick: (Element) -> Unit,
	elementListMode: ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	elementListQuery: String,
	onElementListQueryChange: (String) -> Unit,
	elementName: String,
	onElementNameChange: (String) -> Unit,
	elementValue: String,
	onElementValueChange: (String) -> Unit,
	createElementEnabled: Boolean,
	onCreateElementClick: () -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit,
) {
	ColumnMainScreen(
		expression = expression,
		expressionResult = expressionResult,
		expressionCursorPosition = expressionCursorPosition,
		onExpressionCursorPositionChange = onExpressionCursorPositionChange,
		elementList = elementList,
		onElementListItemClick = onElementListItemClick,
		onElementListItemLongClick = onElementListItemLongClick,
		elementListMode = elementListMode,
		onElementListModeChange = onElementListModeChange,
		elementListQuery = elementListQuery,
		onElementListQueryChange = onElementListQueryChange,
		elementName = elementName,
		onElementNameChange = onElementNameChange,
		elementValue = elementValue,
		onElementValueChange = onElementValueChange,
		createElementEnabled = createElementEnabled,
		onCreateElementClick = onCreateElementClick,
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
	onElementListItemClick: (Element) -> Unit,
	onElementListItemLongClick: (Element) -> Unit,
	elementListMode: ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	elementListQuery: String,
	onElementListQueryChange: (String) -> Unit,
	elementName: String,
	onElementNameChange: (String) -> Unit,
	elementValue: String,
	onElementValueChange: (String) -> Unit,
	createElementEnabled: Boolean,
	onCreateElementClick: () -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit
) {
	BottomSheetScaffold(
		sheetContent = {
			ElementListBottomSheetContent(
				elementList = elementList,
				elementListMode = elementListMode,
				onElementListModeChange = onElementListModeChange,
				onElementListItemClick = onElementListItemClick,
				onElementListItemLongClick = onElementListItemLongClick,
				elementListQuery = elementListQuery,
				onElementListQueryChange = onElementListQueryChange,
				elementName = elementName,
				onElementNameChange = onElementNameChange,
				elementValue = elementValue,
				onElementValueChange = onElementValueChange,
				createElementEnabled = createElementEnabled,
				onCreateElementClick = onCreateElementClick
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
	onElementListItemClick: (Element) -> Unit,
	onElementListItemLongClick: (Element) -> Unit,
	elementListMode: ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	elementListQuery: String,
	onElementListQueryChange: (String) -> Unit,
	elementName: String,
	onElementNameChange: (String) -> Unit,
	elementValue: String,
	onElementValueChange: (String) -> Unit,
	createElementEnabled: Boolean,
	onCreateElementClick: () -> Unit
) {
	val bottomSheetHeight = 512.dp

	ElementList(
		modifier = Modifier
			.height(bottomSheetHeight)
			.fillMaxWidth(),
		elementList = elementList,
		elementListMode = elementListMode,
		onElementListModeChange = onElementListModeChange,
		onElementListItemClick = onElementListItemClick,
		onElementListItemLongClick = onElementListItemLongClick,
		elementListQuery = elementListQuery,
		onElementListQueryChange = onElementListQueryChange,
		elementName = elementName,
		onElementNameChange = onElementNameChange,
		elementValue = elementValue,
		onElementValueChange = onElementValueChange,
		createElementEnabled = createElementEnabled,
		onCreateElementClick = onCreateElementClick
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
