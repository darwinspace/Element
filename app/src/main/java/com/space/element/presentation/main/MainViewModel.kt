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
import com.space.element.presentation.main.model.ExpressionResult.Error
import com.space.element.presentation.main.model.ExpressionResult.Value
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.KeyboardButtonType
import com.space.element.presentation.main.model.Operator
import com.space.element.util.format
import dagger.hilt.android.lifecycle.HiltViewModel
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
		MutableStateFlow<ExpressionResultState>(ExpressionResultState.Empty)
	val expressionResult = _expressionResult.asStateFlow()

	private var _expressionCursorPosition = MutableStateFlow(0)
	val expressionCursorPosition = _expressionCursorPosition.asStateFlow()

	private var _elementListMode = MutableStateFlow<ElementListMode>(ElementListMode.Normal)
	val elementListMode = _elementListMode.asStateFlow()

	private var _elementListQuery = MutableStateFlow(String())
	val elementListQuery = _elementListQuery.asStateFlow()

	private val _elementList = getElementList()
	val elementList =
		combine(_elementList, elementListMode, elementListQuery) { list, mode, query ->
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

	fun onElementListQueryChange(value: String) {
		_elementListQuery.value = value
	}

	fun onElementNameChange(name: String) {
		_elementName.value = name
	}

	fun onElementValueChange(value: String) {
		_elementValue.value = value
	}

	private fun appendExpressionItem(expressionItem: ExpressionItem) {
		expression.add(expressionCursorPosition.value, expressionItem)
	}

	private fun onAppendExpressionItem(expressionItem: ExpressionItem) {
		appendExpressionItem(expressionItem)
		increaseCursor()
		expressionChanged()
	}

	private fun removeItem() {
		val index = expressionCursorPosition.value - 1
		expression.getOrNull(index)?.let {
			expression.removeAt(index)
		}
	}

	fun onKeyboardButtonClick(keyboardButton: KeyboardButton) {
		when (keyboardButton.type) {
			KeyboardButtonType.Dot, KeyboardButtonType.Parentheses, KeyboardButtonType.Operator -> {
				onOperatorButtonClick(keyboardButton)
			}

			KeyboardButtonType.Delete -> {
				onDeleteOperatorButtonClick()
			}

			KeyboardButtonType.Equal -> {
				onEqualOperatorButtonClick()
			}

			KeyboardButtonType.Number -> {
				onNumberButtonClick(keyboardButton)
			}

			KeyboardButtonType.Clear -> {
				onClearButtonClick()
			}
		}
	}

	private fun expressionChanged() {
		val result = evaluateExpression(expression)
		_expressionResult.value = when (result) {
			is Error -> ExpressionResultState.Error(result.exception)
			is Value -> ExpressionResultState.Value(result.value)
		}
	}

	private fun onClearButtonClick() {
		emptyExpression()
		emptyResult()
	}

	private fun onOperatorButtonClick(keyboardButton: KeyboardButton) {
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
		onAppendExpressionItem(item)
	}

	private fun onNumberButtonClick(keyboardButton: KeyboardButton) {
		val item = NumberItem(keyboardButton.symbol)
		onAppendExpressionItem(item)
	}

	private fun onEqualOperatorButtonClick() {
		(expressionResult.value as? ExpressionResultState.Value)?.let { (value) ->
			emptyExpression()
			emptyResult()

			val valueReversed = value.format().reversed()

			valueReversed.forEach { character ->
				if (Operator.Dot.symbol == character) {
					val item = OperatorItem(Operator.Dot)
					appendExpressionItem(item)
				}

				if (character.isDigit()) {
					val item = NumberItem(character)
					appendExpressionItem(item)
				}
			}

			if (value < 0) {
				val item = OperatorItem(Operator.Subtraction)
				appendExpressionItem(item)
			}

			_expressionCursorPosition.value = expression.size
		}
	}

	private fun onDeleteOperatorButtonClick() {
		removeItem()
		decreaseCursor()
		expressionChanged()
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
		_expressionResult.value = ExpressionResultState.Empty
	}

	fun onElementListItemClick(element: Element) {
		val item = ElementItem(element)
		onAppendExpressionItem(item)
	}

	fun onElementListItemLongClick(element: Element) {
		viewModelScope.launch {
			removeElement(element)
		}
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

	fun onExpressionCursorPositionChange(position: Int) {
		_expressionCursorPosition.value = position
	}

	fun onElementListModeChange(mode: ElementListMode) {
		_elementListMode.value = mode
	}

	fun onCreateElementClick() {
		if (elementListMode.value is ElementListMode.Create) {
			addElement(elementName.value, elementValue.value)
			emptyElementName()
			emptyElementValue()
		}

		_elementListMode.value = if (elementListMode.value is ElementListMode.Create) {
			ElementListMode.Normal
		} else {
			ElementListMode.Create
		}
	}

	val isCreateElementButtonEnabled = combine(
		elementList, elementListMode, elementName, elementValue, elementListQuery
	) { list, mode, elementName, elementValue, elementListQuery ->
		when (mode) {
			ElementListMode.Create -> {
				elementName.isNotBlank() &&
						elementValue.toDoubleOrNull() != null &&
						list.none {
							it.name == elementName.trim()
						}
			}

			ElementListMode.Normal -> true
			ElementListMode.Edit -> false
			ElementListMode.Search -> {
				list.none {
					it.name.contains(elementListQuery, ignoreCase = true)
				}
			}
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(),
		initialValue = true
	)
}
