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
import com.space.element.domain.model.ExpressionItem.OperatorItem
import com.space.element.domain.use_case.element_list.AddElementToList
import com.space.element.domain.use_case.element_list.GetElementList
import com.space.element.domain.use_case.element_list.RemoveElementFromList
import com.space.element.domain.use_case.expression.ExecuteExpression
import com.space.element.presentation.main.model.ElementListState
import com.space.element.presentation.main.model.ExpressionResultState
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.KeyboardButtonType
import com.space.element.util.toExpression
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
	private val executeExpression: ExecuteExpression
) : ViewModel() {
	val elementList = getElementList().stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(),
		initialValue = emptyList()
	)

	var elementListState by mutableStateOf<ElementListState>(ElementListState.IdleState)

	val expression = mutableStateListOf<ExpressionItem>(
		OperatorItem(KeyboardButton.Two),
		OperatorItem(KeyboardButton.Multiply),
		OperatorItem(KeyboardButton.Open),
		OperatorItem(KeyboardButton.Two),
		OperatorItem(KeyboardButton.Two),
		OperatorItem(KeyboardButton.Close),
	)

	var expressionResult by mutableStateOf<ExpressionResultState>(ExpressionResultState.Empty)
		private set

	var expressionCursor by mutableStateOf(0)
		private set

	private fun appendExpressionItem(expressionItem: ExpressionItem) {
		expression.add(expressionCursor, expressionItem)
	}

	private fun onAppendExpressionItem(expressionItem: ExpressionItem) {
		emptyResult()
		appendExpressionItem(expressionItem)
		increaseCursor()
	}

	private fun removeItem() {
		val index = expressionCursor - 1
		expression.getOrNull(index)?.let {
			expression.removeAt(index)
		}
	}

	fun onKeyboardButtonClick(keyboardButton: KeyboardButton) {
		when (keyboardButton.type) {
			KeyboardButtonType.Number,
			KeyboardButtonType.Parentheses,
			KeyboardButtonType.Operator -> {
				onOperatorButtonClick(keyboardButton)
			}
			KeyboardButtonType.Delete -> {
				onDeleteOperatorButtonClick()
			}
			KeyboardButtonType.Function -> {
				TODO()
			}
			KeyboardButtonType.Equal -> {
				onEqualOperatorButtonClick()
			}
		}
	}

	/*fun onLongKeyboardButtonClick(keyboardButton: KeyboardButton) {
		/*if (keyboardButton == KeyboardButtonType.Delete) {
			emptyExpression()
			emptyResult()
		}*/
	}*/

	private fun onOperatorButtonClick(operator: KeyboardButton) {
		val item = OperatorItem(operator)
		onAppendExpressionItem(item)
	}

	private fun onEqualOperatorButtonClick() {
		if (expression.isNotEmpty()) {
			val expression = expression.toExpression()
			val result = executeExpression(expression)
			expressionResult = ExpressionResultState.Value(result)
		}
	}

	private fun onDeleteOperatorButtonClick() {
		removeItem()
		decreaseCursor()
		emptyResult()
	}

	private fun increaseCursor() {
		expressionCursor += 1
	}

	private fun decreaseCursor() {
		if (expressionCursor > 0) {
			expressionCursor -= 1
		}
	}

	private fun emptyExpression() {
		expression.clear()
		emptyExpressionCursor()
	}

	fun emptyExpressionCursor() {
		expressionCursor = 0
	}

	private fun emptyResult() {
		expressionResult = ExpressionResultState.Empty
	}

	fun onElementItemClick(element: Element) {
		val item = ElementItem(element)
		onAppendExpressionItem(item)
	}

	fun removeElementItem(element: Element) {
		viewModelScope.launch {
			removeElementFromList(element)
		}
	}

	fun addElement(elementName: String, elementValue: String) {
		viewModelScope.launch {
			val element = Element(elementName, elementValue)
			addElementToList(element)
		}
	}

	fun onExpressionSpaceClick(position: Int) {
		expressionCursor = position
	}
}
