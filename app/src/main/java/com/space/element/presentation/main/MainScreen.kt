package com.space.element.presentation.main

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.domain.model.Element
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.component.ElementHeader
import com.space.element.presentation.main.component.keyboard.ElementKeyboard
import com.space.element.presentation.main.component.keyboard.ElementKeyboardVariant
import com.space.element.presentation.main.component.list.ElementList
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun MainScreenPreview() {
	val expression = remember { mutableStateListOf<ExpressionItem>() }
	var expressionCursorPosition by remember { mutableIntStateOf(expression.size) }

	val elementList = remember { mutableStateListOf<Element>() }
	var elementListMode by remember { mutableStateOf<ElementListMode>(ElementListMode.Normal) }

	ElementTheme {
		MainScreen(
			expression = { expression },
			expressionCursorPosition = { expressionCursorPosition },
			onExpressionCursorPositionChange = { expressionCursorPosition = it },
			expressionResult = { ExpressionResultState.Value(value = 10.0) },
			elementList = { elementList },
			elementListQuery = { String() },
			onElementListQueryChange = { },
			elementListMode = { elementListMode },
			onElementListModeChange = { elementListMode = it },
			onElementListItemLongClick = { },
			onElementListItemClick = { },
			elementName = { String() },
			onElementNameChange = { },
			elementValue = { String() },
			onElementValueChange = { },
			isCreateElementButtonEnabled = { true },
			onCreateElementClick = { },
			onKeyboardButtonClick = { }
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
	val isCreateElementButtonEnabled by viewModel.createButtonEnabled.collectAsState()

	MainScreen(
		expression = { expression },
		expressionResult = { expressionResult },
		expressionCursorPosition = { expressionCursorPosition },
		onExpressionCursorPositionChange = viewModel::onExpressionCursorPositionChange,
		elementList = { elementList },
		onElementListItemClick = viewModel::onElementListItemClick,
		onElementListItemLongClick = viewModel::onElementListItemLongClick,
		elementListMode = { elementListMode },
		onElementListModeChange = viewModel::onElementListModeChange,
		elementListQuery = { elementListQuery },
		onElementListQueryChange = viewModel::onElementListQueryChange,
		elementName = { elementName },
		onElementNameChange = viewModel::onElementNameChange,
		elementValue = { elementValue },
		onElementValueChange = viewModel::onElementValueChange,
		isCreateElementButtonEnabled = { isCreateElementButtonEnabled },
		onCreateElementClick = viewModel::onCreateElementClick,
		onKeyboardButtonClick = viewModel::onKeyboardButtonClick
	)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
	expression: () -> SnapshotStateList<ExpressionItem>,
	expressionCursorPosition: () -> Int,
	onExpressionCursorPositionChange: (Int) -> Unit,
	expressionResult: () -> ExpressionResultState,
	elementList: () -> List<Element>,
	elementListQuery: () -> String,
	onElementListQueryChange: (String) -> Unit,
	elementListMode: () -> ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	onElementListItemLongClick: (Element) -> Unit,
	onElementListItemClick: (Element) -> Unit,
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	isCreateElementButtonEnabled: () -> Boolean,
	onCreateElementClick: () -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit
) {
	BoxWithConstraints {
		val maxHeight = maxHeight
		if (maxWidth >= 720.dp) {
			Surface {
				Row {
					Column(
						modifier = Modifier.weight(1f)
					) {
						ElementHeader(
							modifier = Modifier.weight(1f),
							expression = expression,
							expressionCursorPosition = expressionCursorPosition,
							onExpressionCursorPositionChange = onExpressionCursorPositionChange,
							expressionResultState = expressionResult
						)

						if (maxHeight > 500.dp) {
							ElementKeyboard(
								onButtonClick = onKeyboardButtonClick
							)
						} else if (maxHeight > 400.dp) {
							ElementKeyboardVariant(
								contentGap = 12.dp,
								contentPadding = PaddingValues(12.dp),
								buttonHeight = 48.dp,
								onButtonClick = onKeyboardButtonClick
							)
						} else {
							ElementKeyboardVariant(
								contentGap = 4.dp,
								contentPadding = PaddingValues(4.dp),
								buttonHeight = 48.dp,
								onButtonClick = onKeyboardButtonClick
							)
						}
					}

					Surface(
						modifier = Modifier
							.weight(1f)
							.fillMaxHeight(),
						tonalElevation = 1.dp
					) {
						ElementList(
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
							isCreateElementButtonEnabled = isCreateElementButtonEnabled,
							onCreateElementClick = onCreateElementClick
						)
					}
				}
			}
		} else {
			BottomSheetScaffold(
				sheetContent = {
					ElementList(
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
						isCreateElementButtonEnabled = isCreateElementButtonEnabled,
						onCreateElementClick = onCreateElementClick
					)
				},
				sheetPeekHeight = 48.dp,
				sheetDragHandle = {
					BottomSheetDefaults.DragHandle(
						color = MaterialTheme.colorScheme.onSurface
					)
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
						expressionResultState = expressionResult
					)

					if (maxHeight > 500.dp) {
						ElementKeyboard(
							onButtonClick = onKeyboardButtonClick
						)
					} else if (maxHeight > 400.dp) {
						ElementKeyboardVariant(
							contentGap = 12.dp,
							contentPadding = PaddingValues(12.dp),
							buttonHeight = 48.dp,
							onButtonClick = onKeyboardButtonClick
						)
					} else {
						ElementKeyboardVariant(
							contentGap = 4.dp,
							contentPadding = PaddingValues(4.dp),
							buttonHeight = 48.dp,
							onButtonClick = onKeyboardButtonClick
						)
					}
				}
			}
		}
	}
}
