package com.space.element.presentation.main

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.element.domain.model.Element
import com.space.element.domain.model.ExpressionItem
import com.space.element.domain.model.ExpressionItem.ElementItem
import com.space.element.domain.model.ExpressionItem.NumberItem
import com.space.element.domain.model.ExpressionItem.OperatorItem
import com.space.element.domain.use_case.element_list.AddElement
import com.space.element.domain.use_case.element_list.GetElementList
import com.space.element.domain.use_case.element_list.RemoveElement
import com.space.element.domain.use_case.expression.EvaluateExpression
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ExpressionOperator
import com.space.element.presentation.main.model.ExpressionResult
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.util.format
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
	private val evaluateExpression: EvaluateExpression
) : ViewModel() {
	val expression = mutableStateListOf<ExpressionItem>()

	private var _expressionResult =
		MutableStateFlow<ExpressionResult>(ExpressionResult.Empty)
	val expressionResult = _expressionResult.asStateFlow()

	private var _expressionCursorPosition = MutableStateFlow(0)
	val expressionCursorPosition = _expressionCursorPosition.asStateFlow()

	private var _elementListMode = MutableStateFlow<ElementListMode>(ElementListMode.Normal)
	val elementListMode = _elementListMode.asStateFlow()

	private var _elementListQuery = MutableStateFlow(String())
	val elementListQuery = _elementListQuery.asStateFlow()

	private val _elementList = getElementList()
	val elementList = combine(
		_elementList, elementListMode, elementListQuery
	) { list, mode, query ->
		if (mode is ElementListMode.Search) {
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

	val isCreateElementButtonEnabled = combine(
		elementList, elementListMode, elementName, elementValue, elementListQuery
	) { list, mode, elementName, elementValue, elementListQuery ->
		when (mode) {
			ElementListMode.Create -> {
				elementName.isNotBlank() && elementValue.toDoubleOrNull() != null &&
						list.none { it.name == elementName.trim() }
			}

			ElementListMode.Normal -> true
			ElementListMode.Edit -> false
			ElementListMode.Search -> {
				elementListQuery.isNotBlank() && list.none { it.name == elementListQuery.trim() }
			}
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(),
		initialValue = true
	)

	private fun onExpressionChange() {
		viewModelScope.launch(Dispatchers.IO) {
			_expressionResult.value = evaluateExpression(expression)
		}
	}

	fun onExpressionCursorPositionChange(position: Int) {
		_expressionCursorPosition.value = position
	}

	fun onElementListModeChange(mode: ElementListMode) {
		_elementListMode.value = mode
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

	private fun onAddExpressionItem(expressionItem: ExpressionItem) {
		addExpressionItem(expressionItem)
		increaseCursor()
		onExpressionChange()
	}

	private fun addExpressionItem(expressionItem: ExpressionItem) {
		expression.add(expressionCursorPosition.value, expressionItem)
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
		val operator: ExpressionOperator = when (keyboardButton) {
			KeyboardButton.Open -> ExpressionOperator.Open
			KeyboardButton.Close -> ExpressionOperator.Close
			KeyboardButton.Addition -> ExpressionOperator.Addition
			KeyboardButton.Subtraction -> ExpressionOperator.Subtraction
			KeyboardButton.Multiplication -> ExpressionOperator.Multiplication
			KeyboardButton.Division -> ExpressionOperator.Division
			KeyboardButton.Dot -> ExpressionOperator.Dot
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
		val (value) = expressionResult.value as? ExpressionResult.Value ?: return

		emptyExpression()
		emptyResult()

		value.format().reversed().forEach { character ->
			if (ExpressionOperator.Dot.symbol == character) {
				val item = OperatorItem(ExpressionOperator.Dot)
				addExpressionItem(item)
			}

			if (character.isDigit()) {
				val item = NumberItem(character)
				addExpressionItem(item)
			}
		}

		if (value < 0) {
			val item = OperatorItem(ExpressionOperator.Subtraction)
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
		_expressionResult.value = ExpressionResult.Empty
	}

	private fun addElement(elementName: String, elementValue: String) {
		viewModelScope.launch {
			val element = Element(elementName, elementValue)
			addElement(element)
		}
	}

	private fun emptyElementName() {
		_elementName.value = String()
	}

	private fun emptyElementValue() {
		_elementValue.value = String()
	}

	fun onElementListCreateElementButtonClick() {
		if (elementListMode.value is ElementListMode.Create) {
			addElement(elementName.value, elementValue.value)
			emptyElementName()
			emptyElementValue()
		} else if (elementListMode.value is ElementListMode.Search) {
			_elementName.value = elementListQuery.value
			_elementListQuery.value = String()
		}

		_elementListMode.value = if (elementListMode.value is ElementListMode.Create) {
			ElementListMode.Normal
		} else {
			ElementListMode.Create
		}
	}

	fun onElementListItemClick(element: Element) {
		val item = ElementItem(element)
		onAddExpressionItem(item)
	}
}
