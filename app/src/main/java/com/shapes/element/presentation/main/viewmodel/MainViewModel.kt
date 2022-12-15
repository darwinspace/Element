package com.shapes.element.presentation.main.viewmodel

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shapes.element.domain.data.repository.ElementRepositoryImplementation
import com.shapes.element.domain.model.Element
import com.shapes.element.domain.model.ExpressionItem
import com.shapes.element.domain.model.ExpressionItem.*
import com.shapes.element.domain.repository.ElementRepository
import com.shapes.element.domain.use_case.data.AddToElementList
import com.shapes.element.domain.use_case.data.EditElementFromList
import com.shapes.element.domain.use_case.data.GetElementList
import com.shapes.element.domain.use_case.data.RemoveElementFromList
import com.shapes.element.presentation.main.keyboard.KeyboardButton
import com.shapes.element.presentation.main.keyboard.KeyboardButton.NumberButton
import com.shapes.element.presentation.main.keyboard.KeyboardButton.OperatorButton
import com.shapes.element.presentation.main.keyboard.KeyboardButtonType
import com.shapes.element.presentation.main.keyboard.KeyboardOperator
import com.shapes.expression.ExecuteExpression
import com.shapes.expression.Expression
import kotlinx.coroutines.launch

class MainViewModel(
	private val repository: ElementRepository = ElementRepositoryImplementation()
) : ViewModel() {
	private val executeExpression: ExecuteExpression by lazy { ExecuteExpression() }

	var addition by mutableStateOf(false)
	var search by mutableStateOf(false)

	val expression = mutableStateListOf<ExpressionItem>()

	var cursor by mutableStateOf(0)

	var result by mutableStateOf<ExpressionResultState>(ExpressionResultState.Empty)
		private set

	val elementList: State<List<Element>>
		@Composable
		get() {
			return getElementList(LocalContext.current).collectAsState(emptyList())
		}

	val getElementList by lazy { GetElementList(repository) }
	val addElementToList by lazy { AddToElementList(repository) }
	val editElementFromList by lazy { EditElementFromList(repository) }
	val removeElementFromList by lazy { RemoveElementFromList(repository) }

	private fun addOperator(operator: KeyboardOperator = KeyboardOperator.Multiply) {
		val operatorItem = OperatorItem(operator)
		expression.add(operatorItem)
	}

	private fun appendExpressionItem(expressionItem: ExpressionItem) {
		when (expressionItem) {
			is NumberItem -> {
				onNumberItemOperation(expressionItem)
			}
			is OperatorItem -> {
				onOperatorItemOperation(expressionItem)
			}
			is ElementItem -> {
				onElementItemOperation(expressionItem)
			}
		}
	}

	private fun onAppendExpressionItem(expressionItem: ExpressionItem) {
		emptyResult()
		appendExpressionItem(expressionItem)
		increaseCursor()
	}

	private fun onOperatorItemOperation(operationItem: OperatorItem) {
		expression.add(cursor, operationItem)
	}

	private fun onNumberItemOperation(operationItem: NumberItem) {
		expression.add(cursor, operationItem)
	}

	private fun onElementItemOperation(operationItem: ElementItem) {
		expression.add(cursor, operationItem)
	}

	private fun removeItem() {
		val index = cursor - 1
		expression.getOrNull(index)?.let {
			expression.removeAt(index)
		}
	}

	fun onKeyboardButtonClick(keyboardButton: KeyboardButton) {
		when (keyboardButton) {
			is NumberButton -> {
				onKeyboardNumberButtonClick(keyboardButton)
			}
			is OperatorButton -> {
				onKeyboardOperatorButtonClick(keyboardButton)
			}
		}
	}

	fun onLongKeyboardButtonClick(keyboardButton: KeyboardButton) {
		if (keyboardButton.getType() == KeyboardButtonType.Delete) {
			emptyExpression()
			emptyResult()
		}
	}

	private fun onKeyboardOperatorButtonClick(keyboardButton: OperatorButton) {
		when (val operator = keyboardButton.operator) {
			KeyboardOperator.Delete -> {
				onDeleteOperatorButtonClick()
			}
			KeyboardOperator.Equal -> {
				onEqualOperatorButtonClick()
			}
			else -> {
				onRegularOperatorButtonClick(operator)
			}
		}
	}

	private fun onRegularOperatorButtonClick(operator: KeyboardOperator) {
		val item = OperatorItem(operator)
		onAppendExpressionItem(item)
	}

	private fun onEqualOperatorButtonClick() {
		if (expression.isNotEmpty()) {
			val expressionValue = makeExpression(expression)
			val expression = Expression(expressionValue)
			val expressionResult = executeExpression(expression)
			result = ExpressionResultState.Value(expressionResult)
		}
	}

	private fun onDeleteOperatorButtonClick() {
		removeItem()
		decreaseCursor()
		emptyResult()
	}

	private fun increaseCursor() {
		cursor += 1
	}

	private fun decreaseCursor() {
		if (cursor > 0) {
			cursor -= 1
		}
	}

	private fun onKeyboardNumberButtonClick(button: NumberButton) {
		val item = NumberItem(button.number)
		onAppendExpressionItem(item)
	}

	private fun makeExpression(expressionItems: List<ExpressionItem>): String {
		return expressionItems.joinToString(
			separator = String(),
			transform = ExpressionItem::toString
		)
	}

	private fun emptyExpression() {
		expression.clear()
		cursor = 0
	}

	private fun emptyResult() {
		result = ExpressionResultState.Empty
	}

	fun onElementItemClick(element: Element) {
		val expressionItem = ElementItem(element)
		onAppendExpressionItem(expressionItem)
	}

	fun onLongElementItemClick(context: Context, element: Element) {
		viewModelScope.launch {
			removeElementFromList(context, element)
		}
	}
}
