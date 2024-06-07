package com.space.element.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.domain.model.Element
import com.space.element.domain.model.ElementListItem
import com.space.element.domain.model.ExpressionListItem
import com.space.element.domain.model.Function
import com.space.element.presentation.main.component.Header
import com.space.element.presentation.main.component.Keyboard
import com.space.element.presentation.main.component.Library
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.LibraryState

val SheetPeekHeight = 52.dp
val SheetDragHandleWidth = 32.dp
val SheetDragHandleHeight = 4.dp
val SheetDragHandleTopPadding = (SheetPeekHeight - SheetDragHandleHeight) / 2

@Preview(device = "spec:width=405dp,height=900dp")
@Composable
fun MainScreenPreview() {
//	val expression = remember { mutableStateListOf<ExpressionListItem>() }
//	var expressionCursorPosition by remember { mutableIntStateOf(expression.size) }
//	val expressionResultState = remember { ExpressionResultState.Value(value = 0.0) }
//	val elementList = remember { mutableStateListOf<Element>() }
//	var libraryState by remember { mutableStateOf<LibraryState>(LibraryState.Normal) }
//
//	ElementTheme {
//		MainScreen(
//			expression = { expression },
//			expressionCursorPosition = { expressionCursorPosition },
//			onExpressionCursorPositionChange = { expressionCursorPosition = it },
//			expressionResultState = { expressionResultState },
//			elementList = { elementList },
//			onElementListItemClick = { },
//			elementListQuery = { String() },
//			onElementListQueryChange = { },
//			libraryState = { libraryState },
//			onLibraryStateChange = { libraryState = it },
//			elementName = { String() },
//			onElementNameChange = { },
//			elementValue = { String() },
//			onElementValueChange = { },
//			elementListCreateButtonEnabled = { true },
//			onCreateElementClick = { },
//			onKeyboardButtonClick = { },
//			onRemoveElementClick = { },
//			functionList = { emptyList() },
//			onFunctionListItemClick = { }
//		)
//	}
}

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
	val libraryState by viewModel.libraryState.collectAsState()
	val expression = viewModel.expression
	val expressionCursorPosition by viewModel.expressionCursorPosition.collectAsState()
	val expressionResultState by viewModel.expressionResultState.collectAsState()
	val elementList by viewModel.elementList.collectAsState()
	val elementListQuery by viewModel.elementListQuery.collectAsState()
	val elementListCreateButtonEnabled by viewModel.elementListCreateButtonEnabled.collectAsState()
	val elementName by viewModel.elementName.collectAsState()
	val elementValue by viewModel.elementValue.collectAsState()
	val functionList by viewModel.functionList.collectAsState()
	val functionListCreateButtonEnabled by viewModel.functionListCreateButtonEnabled.collectAsState()
	val functionName by viewModel.functionName.collectAsState()
	val functionDefinition by viewModel.functionDefinition.collectAsState()

	MainScreen(
		libraryState = { libraryState },
		onLibraryStateChange = viewModel::onLibraryStateChange,
		expression = { expression },
		expressionCursorPosition = { expressionCursorPosition },
		onExpressionCursorPositionChange = viewModel::onExpressionCursorPositionChange,
		expressionResultState = { expressionResultState },
		elementList = { elementList },
		onElementListItemClick = viewModel::onElementListItemClick,
		elementListQuery = { elementListQuery },
		onElementListQueryChange = viewModel::onElementListQueryChange,
		elementName = { elementName },
		onElementNameChange = viewModel::onElementNameChange,
		elementValue = { elementValue },
		onElementValueChange = viewModel::onElementValueChange,
		elementListCreateButtonEnabled = { elementListCreateButtonEnabled },
		onCreateElementClick = viewModel::onElementListCreateElementButtonClick,
		onRemoveElementClick = viewModel::onElementListRemoveButtonClick,
		functionList = { functionList },
		onFunctionListItemClick = viewModel::onFunctionListItemClick,
		functionName = { functionName },
		onFunctionNameChange = viewModel::onFunctionNameChange,
		functionDefinition = { functionDefinition },
		onFunctionDefinitionChange = viewModel::onFunctionDefinitionChange,
		functionListCreateButtonEnabled = { functionListCreateButtonEnabled },
		onCreateFunctionClick = viewModel::onFunctionListCreateFunctionButtonClick,
		onKeyboardButtonClick = viewModel::onKeyboardButtonClick
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
	libraryState: () -> LibraryState,
	onLibraryStateChange: (LibraryState) -> Unit,
	expression: () -> SnapshotStateList<ExpressionListItem>,
	expressionCursorPosition: () -> Int,
	onExpressionCursorPositionChange: (Int) -> Unit,
	expressionResultState: () -> ExpressionResultState,
	elementList: () -> List<Element>,
	onElementListItemClick: (Element) -> Unit,
	elementListQuery: () -> String,
	onElementListQueryChange: (String) -> Unit,
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	elementListCreateButtonEnabled: () -> Boolean,
	onCreateElementClick: () -> Unit,
	onRemoveElementClick: (List<ElementListItem>) -> Unit,
	functionList: () -> List<Function>,
	onFunctionListItemClick: (Function) -> Unit,
	functionName: () -> String,
	onFunctionNameChange: (String) -> Unit,
	functionDefinition: () -> String,
	onFunctionDefinitionChange: (String) -> Unit,
	functionListCreateButtonEnabled: () -> Boolean,
	onCreateFunctionClick: () -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit
) {
	/**
	 *  TODO: Everything should be controlled here.
	 *  - Paddings
	 *  - TextStyles
	 *  - Sizes
	 * */
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
				onCreateElementClick = onCreateElementClick,
				onRemoveElementClick = onRemoveElementClick,
				elementListCreateButtonEnabled = elementListCreateButtonEnabled,
				functionList = functionList,
				onFunctionListItemClick = onFunctionListItemClick,
				functionName = functionName,
				onFunctionNameChange = onFunctionNameChange,
				functionDefinition = functionDefinition,
				onFunctionDefinitionChange = onFunctionDefinitionChange,
				functionListCreateButtonEnabled = functionListCreateButtonEnabled,
				onCreateFunctionClick = onCreateFunctionClick
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

			Keyboard(
				onButtonClick = onKeyboardButtonClick
			)
		}
	}
}

