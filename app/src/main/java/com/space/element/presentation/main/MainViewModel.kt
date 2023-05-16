package com.space.element.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.element.domain.model.Element
import com.space.element.domain.model.ExpressionItem
import com.space.element.domain.model.ExpressionItem.ElementItem
import com.space.element.domain.model.ExpressionItem.NumberItem
import com.space.element.domain.model.ExpressionItem.OperatorItem
import com.space.element.domain.use_case.element_list.AddElementToList
import com.space.element.domain.use_case.element_list.GetElementList
import com.space.element.domain.use_case.element_list.RemoveElementFromList
import com.space.element.domain.use_case.expression.EvaluateExpression
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ExpressionResult.Error
import com.space.element.presentation.main.model.ExpressionResult.Value
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.ExpressionResultState.*
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.KeyboardButtonType
import com.space.element.presentation.main.model.Operator
import com.space.element.util.format
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	getElementList: GetElementList,
	private val addElementToList: AddElementToList,
	private val removeElementFromList: RemoveElementFromList,
	private val evaluateExpression: EvaluateExpression
) : ViewModel() {
	val expression = mutableStateListOf<ExpressionItem>()

	var expressionResult by mutableStateOf<ExpressionResultState>(Empty)
		private set

	var expressionCursorPosition by mutableStateOf(0)
		private set

	val elementList = getElementList().stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(),
		initialValue = emptyList()
	)

	var elementListMode by mutableStateOf<ElementListMode>(ElementListMode.Normal)
		private set

	private fun appendExpressionItem(expressionItem: ExpressionItem) {
		expression.add(expressionCursorPosition, expressionItem)
	}

	private fun onAppendExpressionItem(expressionItem: ExpressionItem) {
		appendExpressionItem(expressionItem)
		increaseCursor()
		expressionChanged()
	}

	private fun removeItem() {
		val index = expressionCursorPosition - 1
		expression.getOrNull(index)?.let {
			expression.removeAt(index)
		}
	}

	fun onKeyboardButtonClick(keyboardButton: KeyboardButton) {
		when (keyboardButton.type) {
			KeyboardButtonType.Dot,
			KeyboardButtonType.Parentheses,
			KeyboardButtonType.Operator -> {
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
		expressionResult = when (result) {
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
		(expressionResult as? ExpressionResultState.Value)?.let { (value) ->
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

			expressionCursorPosition = expression.size
		}
	}

	private fun onDeleteOperatorButtonClick() {
		removeItem()
		decreaseCursor()
		expressionChanged()
	}

	private fun increaseCursor() {
		expressionCursorPosition += 1
	}

	private fun decreaseCursor() {
		if (expressionCursorPosition > 0) {
			expressionCursorPosition -= 1
		}
	}

	private fun emptyExpression() {
		expression.clear()
		emptyExpressionCursor()
	}

	private fun emptyExpressionCursor() {
		expressionCursorPosition = 0
	}

	private fun emptyResult() {
		expressionResult = Empty
	}

	fun onElementListItemClick(element: Element) {
		val item = ElementItem(element)
		onAppendExpressionItem(item)
	}

//	fun removeElementItem(element: Element) {
//		viewModelScope.launch {
//			removeElementFromList(element)
//		}
//	}

	fun addElement(elementName: String, elementValue: String) {
		viewModelScope.launch {
			val element = Element(elementName, elementValue)
			addElementToList(element)
		}
	}

	fun onExpressionCursorPositionChange(position: Int) {
		expressionCursorPosition = position
	}

	fun onElementListModeChange(mode: ElementListMode) {
		elementListMode = mode
	}
}
