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
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.component.ElementHeader
import com.space.element.presentation.main.component.ElementKeyboard
import com.space.element.presentation.main.component.ElementKeyboardVariant
import com.space.element.presentation.main.component.ElementList
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ExpressionResult
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.theme.ElementTheme

@Preview(device = "spec:width=405dp,height=900dp")
@Composable
fun MainScreenPreview() {
	val expression = remember { mutableStateListOf<ExpressionItem>() }
	var expressionCursorPosition by remember { mutableIntStateOf(expression.size) }
	val expressionResult = remember { ExpressionResult.Value(value = 0.0) }
	val elementList = remember { mutableStateListOf<Element>() }
	var elementListMode by remember { mutableStateOf<ElementListMode>(ElementListMode.Normal) }

	ElementTheme {
		MainScreen(
			expression = { expression },
			expressionCursorPosition = { expressionCursorPosition },
			onExpressionCursorPositionChange = { expressionCursorPosition = it },
			expressionResult = { expressionResult },
			elementList = { elementList },
			onElementListItemClick = { },
			elementListQuery = { String() },
			onElementListQueryChange = { },
			elementListMode = { elementListMode },
			onElementListModeChange = { elementListMode = it },
			elementName = { String() },
			onElementNameChange = { },
			elementValue = { String() },
			onElementValueChange = { },
			isCreateElementButtonEnabled = { true },
			onCreateElementClick = { },
			onKeyboardButtonClick = { },
			onRemoveClick = { }
		)
	}
}

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
	val expression = viewModel.expression
	val expressionResult by viewModel.expressionResult.collectAsState()
	val expressionCursorPosition by viewModel.expressionCursorPosition.collectAsState()
	val elementList by viewModel.elementList.collectAsState()
	val elementListQuery by viewModel.elementListQuery.collectAsState()
	val elementListMode by viewModel.elementListMode.collectAsState()
	val elementName by viewModel.elementName.collectAsState()
	val elementValue by viewModel.elementValue.collectAsState()
	val isCreateElementButtonEnabled by viewModel.isCreateElementButtonEnabled.collectAsState()

	MainScreen(
		expression = { expression },
		expressionCursorPosition = { expressionCursorPosition },
		onExpressionCursorPositionChange = viewModel::onExpressionCursorPositionChange,
		expressionResult = { expressionResult },
		elementList = { elementList },
		onElementListItemClick = viewModel::onElementListItemClick,
		elementListQuery = { elementListQuery },
		onElementListQueryChange = viewModel::onElementListQueryChange,
		elementListMode = { elementListMode },
		onElementListModeChange = viewModel::onElementListModeChange,
		elementName = { elementName },
		onElementNameChange = viewModel::onElementNameChange,
		elementValue = { elementValue },
		onElementValueChange = viewModel::onElementValueChange,
		isCreateElementButtonEnabled = { isCreateElementButtonEnabled },
		onCreateElementClick = viewModel::onElementListCreateElementButtonClick,
		onKeyboardButtonClick = viewModel::onKeyboardButtonClick,
		onRemoveClick = viewModel::onRemoveClick
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
	expression: () -> SnapshotStateList<ExpressionItem>,
	expressionCursorPosition: () -> Int,
	onExpressionCursorPositionChange: (Int) -> Unit,
	expressionResult: () -> ExpressionResult,
	elementList: () -> List<Element>,
	onElementListItemClick: (Element) -> Unit,
	elementListQuery: () -> String,
	onElementListQueryChange: (String) -> Unit,
	elementListMode: () -> ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	isCreateElementButtonEnabled: () -> Boolean,
	onCreateElementClick: () -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit,
	onRemoveClick: (List<ElementListItem>) -> Unit
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
			val sheetPeekHeight = 52.dp
			val sheetDragHandleWidth = 32.dp
			val sheetDragHandleHeight = 4.dp
			val sheetDragHandleTopPadding = (sheetPeekHeight - sheetDragHandleHeight) / 2
			BottomSheetScaffold(
				sheetContent = {
					ElementList(
						elementList = elementList,
						onElementListItemClick = onElementListItemClick,
						elementListQuery = elementListQuery,
						onElementListQueryChange = onElementListQueryChange,
						elementListMode = elementListMode,
						onElementListModeChange = onElementListModeChange,
						elementName = elementName,
						onElementNameChange = onElementNameChange,
						elementValue = elementValue,
						onElementValueChange = onElementValueChange,
						isCreateElementButtonEnabled = isCreateElementButtonEnabled,
						onCreateElementClick = onCreateElementClick,
						onRemoveClick = onRemoveClick
					)
				},
				sheetPeekHeight = sheetPeekHeight,
				sheetDragHandle = {
					Surface(
						modifier = Modifier.padding(top = sheetDragHandleTopPadding),
						color = MaterialTheme.colorScheme.onSurface,
						shape = MaterialTheme.shapes.extraLarge
					) {
						Box(
							modifier = Modifier.size(
								width = sheetDragHandleWidth,
								height = sheetDragHandleHeight
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
					ElementHeader(
						modifier = Modifier.weight(1f),
						expression = expression,
						expressionCursorPosition = expressionCursorPosition,
						onExpressionCursorPositionChange = onExpressionCursorPositionChange,
						expressionResult = expressionResult
					)

					if (maxHeight > 500.dp) {
						ElementKeyboard(
							onButtonClick = onKeyboardButtonClick
						)
					} else if (maxHeight > 400.dp) {
						ElementKeyboardVariant(
							contentGap = 12.dp,
							contentPadding = PaddingValues(12.dp),
							onButtonClick = onKeyboardButtonClick
						)
					} else {
						ElementKeyboardVariant(
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
						ElementHeader(
							modifier = Modifier.weight(1f),
							expression = expression,
							expressionCursorPosition = expressionCursorPosition,
							onExpressionCursorPositionChange = onExpressionCursorPositionChange,
							expressionResult = expressionResult
						)

						if (maxHeight > 500.dp) {
							ElementKeyboard(
								modifier = Modifier.background(Color.Blue),
								onButtonClick = onKeyboardButtonClick
							)
						} else if (maxHeight > 400.dp) {
							ElementKeyboardVariant(
								modifier = Modifier.background(Color.Yellow),
								contentGap = 12.dp,
								contentPadding = PaddingValues(12.dp),
								onButtonClick = onKeyboardButtonClick
							)
						} else {
							ElementKeyboardVariant(
								modifier = Modifier.background(Color.Magenta),
								contentGap = 4.dp,
								contentPadding = PaddingValues(4.dp),
								onButtonClick = onKeyboardButtonClick
							)
						}
					}
				}

				ElementList(
					modifier = Modifier
						.weight(1f)
						.fillMaxHeight(),
					elementList = elementList,
					onElementListItemClick = onElementListItemClick,
					elementListQuery = elementListQuery,
					onElementListQueryChange = onElementListQueryChange,
					elementListMode = elementListMode,
					onElementListModeChange = onElementListModeChange,
					elementName = elementName,
					onElementNameChange = onElementNameChange,
					elementValue = elementValue,
					onElementValueChange = onElementValueChange,
					isCreateElementButtonEnabled = isCreateElementButtonEnabled,
					onCreateElementClick = onCreateElementClick,
					onRemoveClick = onRemoveClick
				)
			}
		}
	}
}

