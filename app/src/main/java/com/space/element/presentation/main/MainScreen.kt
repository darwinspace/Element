package com.space.element.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.domain.model.Element
import com.space.element.domain.model.ElementListItem
import com.space.element.domain.model.ExpressionListItem
import com.space.element.domain.model.Function
import com.space.element.presentation.main.component.Header
import com.space.element.presentation.main.component.Keyboard
import com.space.element.presentation.main.component.KeyboardVariant
import com.space.element.presentation.main.component.Library
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.LibraryState
import com.space.element.presentation.theme.ElementTheme


val SheetPeekHeight = 52.dp
val SheetDragHandleWidth = 32.dp
val SheetDragHandleHeight = 4.dp
val SheetDragHandleTopPadding = (SheetPeekHeight - SheetDragHandleHeight) / 2

@Preview(device = "spec:width=405dp,height=900dp")
@Composable
fun MainScreenPreview() {
	val expression = remember { mutableStateListOf<ExpressionListItem>() }
	var expressionCursorPosition by remember { mutableIntStateOf(expression.size) }
	val expressionResultState = remember { ExpressionResultState.Value(value = 0.0) }
	val elementList = remember { mutableStateListOf<Element>() }
	var libraryState by remember { mutableStateOf<LibraryState>(LibraryState.Normal) }

	ElementTheme {
		MainScreen(
			expression = { expression },
			expressionCursorPosition = { expressionCursorPosition },
			onExpressionCursorPositionChange = { expressionCursorPosition = it },
			expressionResultState = { expressionResultState },
			elementList = { elementList },
			onElementListItemClick = { },
			elementListQuery = { String() },
			onElementListQueryChange = { },
			libraryState = { libraryState },
			onLibraryStateChange = { libraryState = it },
			elementName = { String() },
			onElementNameChange = { },
			elementValue = { String() },
			onElementValueChange = { },
			isCreateElementButtonEnabled = { true },
			onCreateElementClick = { },
			onKeyboardButtonClick = { },
			onRemoveClick = { },
			functionList = { emptyList() },
			onFunctionListItemClick = { }
		)
	}
}

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
	val expression = viewModel.expression
	val expressionResult by viewModel.expressionResultState.collectAsState()
	val expressionCursorPosition by viewModel.expressionCursorPosition.collectAsState()
	val isCreateElementButtonEnabled by viewModel.isCreateElementButtonEnabled.collectAsState()
	val elementList by viewModel.elementList.collectAsState()
	val elementListQuery by viewModel.elementListQuery.collectAsState()
	val elementListMode by viewModel.elementListMode.collectAsState()
	val elementName by viewModel.elementName.collectAsState()
	val elementValue by viewModel.elementValue.collectAsState()
	val functionList = remember {
		List(10) {
			Function("fn${it + 1}", "x*${it + 1}")
		}
	}

	MainScreen(
		expression = { expression },
		expressionCursorPosition = { expressionCursorPosition },
		onExpressionCursorPositionChange = viewModel::onExpressionCursorPositionChange,
		expressionResultState = { expressionResult },
		elementList = { elementList },
		onElementListItemClick = viewModel::onElementListItemClick,
		elementListQuery = { elementListQuery },
		onElementListQueryChange = viewModel::onElementListQueryChange,
		libraryState = { elementListMode },
		onLibraryStateChange = viewModel::onLibraryStateChange,
		elementName = { elementName },
		onElementNameChange = viewModel::onElementNameChange,
		elementValue = { elementValue },
		onElementValueChange = viewModel::onElementValueChange,
		isCreateElementButtonEnabled = { isCreateElementButtonEnabled },
		onCreateElementClick = viewModel::onElementListCreateElementButtonClick,
		onKeyboardButtonClick = viewModel::onKeyboardButtonClick,
		onRemoveClick = viewModel::onElementListRemoveButtonClick,
		functionList = { functionList },
		onFunctionListItemClick = viewModel::onFunctionListItemClick
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
	expression: () -> SnapshotStateList<ExpressionListItem>,
	expressionCursorPosition: () -> Int,
	onExpressionCursorPositionChange: (Int) -> Unit,
	expressionResultState: () -> ExpressionResultState,
	elementList: () -> List<Element>,
	onElementListItemClick: (Element) -> Unit,
	elementListQuery: () -> String,
	onElementListQueryChange: (String) -> Unit,
	libraryState: () -> LibraryState,
	onLibraryStateChange: (LibraryState) -> Unit,
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	isCreateElementButtonEnabled: () -> Boolean,
	onCreateElementClick: () -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit,
	onRemoveClick: (List<ElementListItem>) -> Unit,
	functionList: () -> List<Function>,
	onFunctionListItemClick: (Function) -> Unit
) {
	/**
	 *  TODO: Everything should be controlled here.
	 *  - Paddings
	 *  - TextStyles
	 *  - Sizes
	 * */
	BoxWithConstraints {
		val maxHeight = maxHeight
		if (maxWidth < 720.dp) {
			BottomSheetScaffold(
				sheetContent = {
					Library(
						libraryState = libraryState,
						onLibraryStateChange = onLibraryStateChange,
						elementList = elementList,
						onElementListItemClick = onElementListItemClick,
						elementListQuery = elementListQuery,
						onElementListQueryChange = onElementListQueryChange,
						elementName = elementName,
						onElementNameChange = onElementNameChange,
						elementValue = elementValue,
						onElementValueChange = onElementValueChange,
						isCreateElementButtonEnabled = isCreateElementButtonEnabled,
						onCreateElementClick = onCreateElementClick,
						onRemoveClick = onRemoveClick,
						functionList = functionList,
						onFunctionListItemClick = onFunctionListItemClick
					)
				},
				sheetPeekHeight = SheetPeekHeight,
				sheetDragHandle = {
					Surface(
						modifier = Modifier.padding(top = SheetDragHandleTopPadding),
						color = MaterialTheme.colorScheme.onSurface,
						shape = MaterialTheme.shapes.extraLarge
					) {
						Box(
							modifier = Modifier.size(
								width = SheetDragHandleWidth,
								height = SheetDragHandleHeight
							)
						)
					}
				}
			) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(it)
				) {
					Header(
						modifier = Modifier.weight(1f),
						expression = expression,
						expressionCursorPosition = expressionCursorPosition,
						onExpressionCursorPositionChange = onExpressionCursorPositionChange,
						expressionResultState = expressionResultState
					)

					if (maxHeight > 500.dp) {
						Keyboard(
							onButtonClick = onKeyboardButtonClick
						)
					} else if (maxHeight > 400.dp) {
						KeyboardVariant(
							contentGap = 12.dp,
							contentPadding = PaddingValues(12.dp),
							onButtonClick = onKeyboardButtonClick
						)
					} else {
						KeyboardVariant(
							contentGap = 4.dp,
							contentPadding = PaddingValues(4.dp),
							onButtonClick = onKeyboardButtonClick
						)
					}
				}
			}
		} else {
			Row {
				Surface(
					modifier = Modifier.weight(1f)
				) {
					Column {
						Header(
							modifier = Modifier.weight(1f),
							expression = expression,
							expressionCursorPosition = expressionCursorPosition,
							onExpressionCursorPositionChange = onExpressionCursorPositionChange,
							expressionResultState = expressionResultState
						)

						if (maxHeight > 500.dp) {
							Keyboard(
								modifier = Modifier.background(Color.Blue),
								onButtonClick = onKeyboardButtonClick
							)
						} else if (maxHeight > 400.dp) {
							KeyboardVariant(
								modifier = Modifier.background(Color.Yellow),
								contentGap = 12.dp,
								contentPadding = PaddingValues(12.dp),
								onButtonClick = onKeyboardButtonClick
							)
						} else {
							KeyboardVariant(
								modifier = Modifier.background(Color.Magenta),
								contentGap = 4.dp,
								contentPadding = PaddingValues(4.dp),
								onButtonClick = onKeyboardButtonClick
							)
						}
					}
				}

				Library(
					libraryState = libraryState,
					onLibraryStateChange = onLibraryStateChange,
					modifier = Modifier
						.weight(1f)
						.fillMaxHeight(),
					elementList = elementList,
					onElementListItemClick = onElementListItemClick,
					elementListQuery = elementListQuery,
					onElementListQueryChange = onElementListQueryChange,
					elementName = elementName,
					onElementNameChange = onElementNameChange,
					elementValue = elementValue,
					onElementValueChange = onElementValueChange,
					isCreateElementButtonEnabled = isCreateElementButtonEnabled,
					onRemoveClick = onRemoveClick,
					onCreateElementClick = onCreateElementClick,
					functionList = functionList,
					onFunctionListItemClick = onFunctionListItemClick
				)
			}
		}
	}
}

