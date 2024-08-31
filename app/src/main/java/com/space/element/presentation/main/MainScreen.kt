package com.space.element.presentation.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.domain.model.Element
import com.space.element.domain.model.ElementListItem
import com.space.element.domain.model.ExpressionListItem
import com.space.element.domain.model.Function
import com.space.element.domain.model.FunctionListItem
import com.space.element.presentation.main.component.Header
import com.space.element.presentation.main.component.Keyboard
import com.space.element.presentation.main.component.Library
import com.space.element.presentation.main.component.LibraryDragHandle
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.LibraryState
import com.space.element.presentation.theme.ElementTheme

val SheetPeekHeight = 52.dp
val SheetDragHandleWidth = 32.dp
val SheetDragHandleHeight = 4.dp
val SheetDragHandleTopPadding = (SheetPeekHeight - SheetDragHandleHeight) / 2

@Preview
@Composable
fun MainScreenPreview() {
	var libraryState by remember { mutableStateOf<LibraryState>(LibraryState.ElementList) }
	val expression = remember { mutableStateListOf<ExpressionListItem>() }
	var expressionCursorPosition by remember { mutableIntStateOf(expression.size) }
	val expressionResultState = remember { ExpressionResultState.Value(value = 0.0) }
	val elementList = remember { mutableStateListOf<Element>() }

	ElementTheme {
		MainScreen(
			libraryState = { libraryState },
			onLibraryStateChange = { libraryState = it },
			expression = { expression },
			expressionCursorPosition = { expressionCursorPosition },
			onExpressionCursorPositionChange = { expressionCursorPosition = it },
			expressionResultState = { expressionResultState },
			elementList = { elementList },
			onElementListItemClick = { },
			elementListQuery = { String() },
			onElementListQueryChange = { },
			elementName = { String() },
			onElementNameChange = { },
			elementValue = { String() },
			onElementValueChange = { },
			elementListCreateButtonEnabled = { true },
			onCreateElementClick = { },
			onRemoveElementClick = { },
			functionList = { emptyList() },
			onFunctionListItemClick = { },
			functionName = { String() },
			onFunctionNameChange = { },
			functionDefinition = { String() },
			onFunctionDefinitionChange = { },
			functionListCreateButtonEnabled = { true },
			onCreateFunctionClick = { },
			onRemoveFunctionClick = { },
			onKeyboardButtonClick = { }
		)
	}
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
		onRemoveFunctionClick = viewModel::onFunctionListRemoveButtonClick,
		onKeyboardButtonClick = viewModel::onKeyboardButtonClick
	)
}

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
	onRemoveFunctionClick: (List<FunctionListItem>) -> Unit,
	onKeyboardButtonClick: (KeyboardButton) -> Unit
) {
	/**
	 *  TODO: Everything should be controlled here.
	 *  - Paddings
	 *  - TextStyles
	 *  - Sizes
	 * */
	MainScreenScaffold(
		header = {
			Header(
				modifier = Modifier.weight(1f),
				expression = expression,
				expressionCursorPosition = expressionCursorPosition,
				onExpressionCursorPositionChange = onExpressionCursorPositionChange,
				expressionResultState = expressionResultState
			)
		},
		keyboard = {
			Keyboard(
				onButtonClick = onKeyboardButtonClick
			)
		},
		sheetDragHandle = {
			LibraryDragHandle(
				modifier = Modifier.padding(top = SheetDragHandleTopPadding),
				width = SheetDragHandleWidth,
				height = SheetDragHandleHeight,
			)
		},
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
				onCreateFunctionClick = onCreateFunctionClick,
				onRemoveFunctionClick = onRemoveFunctionClick,
			)
		}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenScaffold(
	header: @Composable (ColumnScope.() -> Unit),
	keyboard: @Composable () -> Unit,
	sheetDragHandle: @Composable (() -> Unit),
	sheetContent: @Composable ColumnScope.() -> Unit,
) {
	BottomSheetScaffold(
		sheetContent = {
			Column(
				modifier = Modifier.border(
					width = 2.dp,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
					shape = MaterialTheme.shapes.extraLarge.copy(
						bottomStart = CornerSize(0.dp),
						bottomEnd = CornerSize(0.dp),
					),
				),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(0.dp),
			) {
				sheetDragHandle()
				sheetContent()
			}
		},
		sheetContainerColor = MaterialTheme.colorScheme.surface,
		sheetDragHandle = null,
		sheetTonalElevation = 0.dp,
		sheetShadowElevation = 0.dp,
		sheetPeekHeight = SheetPeekHeight
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
		) {
			header()
			keyboard()
		}
	}
}

