package com.space.element.presentation.main

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.element.domain.model.Element
import com.space.element.domain.model.ElementListItem
import com.space.element.domain.model.ExpressionListItem
import com.space.element.domain.model.ExpressionListItem.ElementItem
import com.space.element.domain.model.ExpressionListItem.FunctionItem
import com.space.element.domain.model.ExpressionListItem.NumberItem
import com.space.element.domain.model.ExpressionListItem.OperatorItem
import com.space.element.domain.model.Function
import com.space.element.domain.model.Operator
import com.space.element.domain.use_case.element_list.AddElement
import com.space.element.domain.use_case.element_list.GetElementList
import com.space.element.domain.use_case.element_list.RemoveElement
import com.space.element.domain.use_case.expression.EvaluateExpression
import com.space.element.domain.use_case.function_list.AddFunction
import com.space.element.domain.use_case.function_list.GetFunctionList
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.LibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	getElementList: GetElementList,
	private val addElement: AddElement,
	private val removeElement: RemoveElement,
	private val evaluateExpression: EvaluateExpression,
	getFunctionList: GetFunctionList,
	private val addFunction: AddFunction
) : ViewModel() {
	private var _libraryState = MutableStateFlow<LibraryState>(LibraryState.ElementList)
	val libraryState = _libraryState.asStateFlow()

	val expression = mutableStateListOf<ExpressionListItem>()

	private var _expressionCursorPosition = MutableStateFlow(0)
	val expressionCursorPosition = _expressionCursorPosition.asStateFlow()

	private var _expressionResultState =
		MutableStateFlow<ExpressionResultState>(ExpressionResultState.Empty)
	val expressionResultState = _expressionResultState.asStateFlow()

	private var _elementListQuery = MutableStateFlow(String())
	val elementListQuery = _elementListQuery.asStateFlow()

	private val _elementList = getElementList()
	val elementList = combine(
		_elementList, libraryState, elementListQuery
	) { list, mode, query ->
		if (mode is LibraryState.SearchElement) {
			list.filter { it.name.contains(query, ignoreCase = true) }
		} else {
			list
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(),
		initialValue = emptyList()
	)

	private var _elementName = MutableStateFlow(String())
	val elementName = _elementName.asStateFlow()

	private var _elementValue = MutableStateFlow(String())
	val elementValue = _elementValue.asStateFlow()

	val elementListCreateButtonEnabled = combine(
		elementList, libraryState, elementName, elementValue, elementListQuery
	) { list, state, elementName, elementValue, elementListQuery ->
		when (state) {
			LibraryState.CreateElement -> {
				elementName.isNotBlank() && elementValue.toDoubleOrNull() != null &&
						list.none { it.name.trim() == elementName.trim() }
			}

			LibraryState.ElementList -> true
			LibraryState.SearchElement -> {
				elementListQuery.isNotBlank() &&
						list.none { it.name.trim() == elementListQuery.trim() }
			}

			else -> false
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(),
		initialValue = true
	)

	private val _functionList = getFunctionList()
	val functionList = _functionList.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(),
		initialValue = emptyList()
	)

	private var _functionName = MutableStateFlow(String())
	val functionName = _functionName.asStateFlow()

	private var _functionDefinition = MutableStateFlow(String())
	val functionDefinition = _functionDefinition.asStateFlow()

	val functionListCreateButtonEnabled = combine(
		functionList,
		libraryState,
		functionName,
		functionDefinition
	) { list, state, name, definition ->
		when (state) {
			LibraryState.CreateFunction -> {
				name.isNotBlank() && definition.isNotBlank() &&
						list.none { it.name.trim() == name.trim() }
			}

			LibraryState.FunctionList -> true
			else -> false
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(),
		initialValue = true
	)

	private fun onExpressionChange() {
		viewModelScope.launch(Dispatchers.IO) {
			_expressionResultState.value = evaluateExpression(expression)
		}
	}

	fun onExpressionCursorPositionChange(position: Int) {
		_expressionCursorPosition.value = position
	}

	fun onLibraryStateChange(mode: LibraryState) {
		_libraryState.value = mode
	}

	fun onElementListQueryChange(value: String) {
		_elementListQuery.value = value
	}

	fun onElementNameChange(name: String) {
		_elementName.value = name
	}

	fun onElementValueChange(value: String) {
		_elementValue.value = value
	}

	fun onFunctionNameChange(name: String) {
		_functionName.value = name
	}

	fun onFunctionDefinitionChange(definition: String) {
		_functionDefinition.value = definition
	}

	private fun onAddExpressionItem(expressionListItem: ExpressionListItem) {
		if (expressionListItem is FunctionItem) {
			addExpressionItem(OperatorItem(Operator.Close))
			addExpressionItem(OperatorItem(Operator.Open))
		}
		addExpressionItem(expressionListItem)
		if (expressionListItem is FunctionItem) {
			increaseCursor()
		}
		increaseCursor()
		onExpressionChange()
	}

	private fun addExpressionItem(expressionListItem: ExpressionListItem) {
		expression.add(expressionCursorPosition.value, expressionListItem)
	}

	private fun removeExpressionItem() {
		val index = expressionCursorPosition.value - 1
		expression.getOrNull(index)?.let { expression.removeAt(index) }
	}

	fun onKeyboardButtonClick(keyboardButton: KeyboardButton) {
		when (keyboardButton.type) {
			KeyboardButton.Type.Dot,
			KeyboardButton.Type.Parentheses,
			KeyboardButton.Type.Operator -> {
				onKeyboardOperatorButtonClick(keyboardButton)
			}

			KeyboardButton.Type.Delete -> {
				onKeyboardDeleteOperatorButtonClick()
			}

			KeyboardButton.Type.Equal -> {
				onKeyboardEqualOperatorButtonClick()
			}

			KeyboardButton.Type.Number -> {
				onKeyboardNumberButtonClick(keyboardButton)
			}

			KeyboardButton.Type.Clear -> {
				onKeyboardClearButtonClick()
			}
		}
	}

	private fun onKeyboardNumberButtonClick(keyboardButton: KeyboardButton) {
		val item = NumberItem(keyboardButton.symbol)
		onAddExpressionItem(item)
	}

	private fun onKeyboardOperatorButtonClick(keyboardButton: KeyboardButton) {
		val operator: Operator = when (keyboardButton) {
			KeyboardButton.Open -> Operator.Open
			KeyboardButton.Close -> Operator.Close
			KeyboardButton.Addition -> Operator.Addition
			KeyboardButton.Subtraction -> Operator.Subtraction
			KeyboardButton.Multiplication -> Operator.Multiplication
			KeyboardButton.Division -> Operator.Division
			KeyboardButton.Dot -> Operator.Dot
			else -> throw IllegalStateException()
		}

		val item = OperatorItem(operator)
		onAddExpressionItem(item)
	}

	private fun onKeyboardDeleteOperatorButtonClick() {
		removeExpressionItem()
		decreaseCursor()
		onExpressionChange()
	}

	private fun onKeyboardClearButtonClick() {
		emptyExpression()
		emptyResult()
	}

	private fun onKeyboardEqualOperatorButtonClick() {
		val (value) = expressionResultState.value as? ExpressionResultState.Value ?: return

		emptyExpression()
		emptyResult()

		value.toString().reversed().forEach { character ->
			if (character == Operator.Dot.symbol) {
				val item = OperatorItem(Operator.Dot)
				addExpressionItem(item)
			}

			if (character.isDigit()) {
				val item = NumberItem(character)
				addExpressionItem(item)
			}
		}

		if (value < 0) {
			val item = OperatorItem(Operator.Subtraction)
			addExpressionItem(item)
		}

		_expressionCursorPosition.value = expression.size
	}

	private fun increaseCursor() {
		_expressionCursorPosition.value += 1
	}

	private fun decreaseCursor() {
		if (expressionCursorPosition.value > 0) {
			_expressionCursorPosition.value -= 1
		}
	}

	private fun emptyExpression() {
		expression.clear()
		emptyExpressionCursor()
	}

	private fun emptyExpressionCursor() {
		_expressionCursorPosition.value = 0
	}

	private fun emptyResult() {
		_expressionResultState.value = ExpressionResultState.Empty
	}

	private fun addElement(elementName: String, elementValue: String) {
		viewModelScope.launch {
			val element = Element(elementName.trim(), elementValue)
			addElement(element)
		}
	}

	private fun emptyElementName() {
		_elementName.value = String()
	}

	private fun emptyElementValue() {
		_elementValue.value = String()
	}

	private fun emptyFunctionName() {
		_functionName.value = String()
	}

	private fun emptyFunctionDefinition() {
		_functionDefinition.value = String()
	}

	private fun addFunction(functionName: String, functionDefinition: String) {
		viewModelScope.launch {
			val function = Function(functionName.trim(), functionDefinition)
			addFunction(function)
		}
	}

	fun onElementListCreateElementButtonClick() {
		if (libraryState.value is LibraryState.CreateElement) {
			addElement(elementName.value, elementValue.value)
			emptyElementName()
			emptyElementValue()
		} else if (libraryState.value is LibraryState.SearchElement) {
			_elementName.value = elementListQuery.value
			_elementListQuery.value = String()
		}

		_libraryState.value = if (libraryState.value is LibraryState.CreateElement) {
			LibraryState.ElementList
		} else {
			LibraryState.CreateElement
		}
	}

	fun onElementListItemClick(element: Element) {
		val item = ElementItem(element)
		onAddExpressionItem(item)
	}

	fun onElementListRemoveButtonClick(list: List<ElementListItem>) {
		viewModelScope.launch {
			list.filter { it.selected }.forEach { removeElement(it.element) }
		}
	}

	fun onFunctionListItemClick(function: Function) {
		val item = FunctionItem(function)
		onAddExpressionItem(item)
	}

	fun onFunctionListCreateFunctionButtonClick() {
		if (libraryState.value is LibraryState.CreateFunction) {
			addFunction(functionName.value, functionDefinition.value)
			emptyFunctionName()
			emptyFunctionDefinition()
		}

		if (libraryState.value is LibraryState.CreateFunction) {
			_libraryState.value = LibraryState.FunctionList
		} else {
			_libraryState.value = LibraryState.CreateFunction
		}
	}
}
