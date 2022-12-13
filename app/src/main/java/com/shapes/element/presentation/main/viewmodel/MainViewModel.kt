package com.shapes.element.presentation.main.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shapes.element.domain.data.repository.ElementRepositoryImplementation
import com.shapes.element.domain.model.Element
import com.shapes.element.domain.model.ExpressionItem
import com.shapes.element.domain.model.ExpressionItem.*
import com.shapes.element.domain.repository.ElementRepository
import com.shapes.element.presentation.main.keyboard.KeyboardButton
import com.shapes.element.presentation.main.keyboard.KeyboardButton.NumberButton
import com.shapes.element.presentation.main.keyboard.KeyboardButton.OperatorButton
import com.shapes.element.presentation.main.keyboard.KeyboardButtonType
import com.shapes.element.presentation.main.keyboard.KeyboardOperator
import com.shapes.expression.ExecuteExpression
import com.shapes.expression.Expression
import kotlinx.coroutines.flow.Flow

class MainViewModel(
	private val repository: ElementRepository = ElementRepositoryImplementation()
) : ViewModel() {
	private val executeExpression: ExecuteExpression by lazy { ExecuteExpression() }

	var addition by mutableStateOf(false)
	var search by mutableStateOf(false)

	val expression = mutableStateListOf<ExpressionItem>()

	var result by mutableStateOf<ExpressionResultState>(ExpressionResultState.Empty)
		private set

	fun getElementList(context: Context): Flow<List<Element>> {
		return repository.getElementList(context)
	}

	suspend fun addElementItem(
		context: Context,
		elementList: List<Element>,
		element: Element
	) {
		val list = listOf(element) + elementList
		repository.setElementList(context, list)
	}

	suspend fun removeElementItem(
		context: Context,
		elementList: List<Element>,
		element: Element
	) {
		val list = elementList - element
		repository.setElementList(context, list)
	}

	private fun addOperator(operator: KeyboardOperator = KeyboardOperator.Multiply) {
		val operatorItem = OperatorItem(operator)
		expression.add(operatorItem)
	}

	fun appendExpressionItem(expressionItem: ExpressionItem) {
		emptyResult()
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

	private fun onOperatorItemOperation(operationItem: OperatorItem) {
		expression.add(operationItem)
	}

	private fun onNumberItemOperation(operationItem: NumberItem) {
		if (expression.isNotEmpty()) {
			val previous = expression.last()
			val isAppendable = previous == OperatorItem(KeyboardOperator.Close)
					|| previous is ElementItem
			if (isAppendable) {
				addOperator()
			}
		}

		expression.add(operationItem)
	}

	private fun isAppendable(operation: ExpressionItem): Boolean {
		return operation == OperatorItem(KeyboardOperator.Close)
				|| operation is NumberItem
				|| operation is ElementItem
	}

	private fun onElementItemOperation(operationItem: ElementItem) {
		if (expression.isNotEmpty() && isAppendable(expression.last())) {
			val keyboardOperator = if (expression.last() is ElementItem) {
				KeyboardOperator.Add
			} else {
				KeyboardOperator.Multiply
			}

			addOperator(keyboardOperator)
		}

		expression.add(operationItem)
	}

	private fun removeLastItem() {
		expression.removeLastOrNull()
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
		appendExpressionItem(item)
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
		removeLastItem()
	}

	private fun onKeyboardNumberButtonClick(button: NumberButton) {
		val item = NumberItem(button.number)
		appendExpressionItem(item)
	}

	private fun makeExpression(expressionItems: List<ExpressionItem>): String {
		return expressionItems.joinToString(separator = String()) { operationItem ->
			when (operationItem) {
				is ElementItem -> "(${operationItem.element.value})"
				is NumberItem -> operationItem.number
				is OperatorItem -> operationItem.operator.symbol
			}.toString()
		}
	}

	private fun emptyExpression() {
		expression.clear()
	}

	private fun emptyResult() {
		result = ExpressionResultState.Empty
	}
}
