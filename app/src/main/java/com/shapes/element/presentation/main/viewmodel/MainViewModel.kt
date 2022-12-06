package com.shapes.element.presentation.main.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shapes.element.domain.data.repository.ApplicationRepositoryImplementation
import com.shapes.element.domain.model.ElementItemData
import com.shapes.element.domain.model.OperationItem
import com.shapes.element.domain.model.OperationItem.*
import com.shapes.element.domain.repository.ApplicationRepository
import com.shapes.element.presentation.main.keyboard.KeyboardButton
import com.shapes.element.presentation.main.keyboard.KeyboardButton.NumberButton
import com.shapes.element.presentation.main.keyboard.KeyboardButton.OperatorButton
import com.shapes.element.presentation.main.keyboard.KeyboardOperator
import com.shapes.expression.ExecuteExpression
import com.shapes.expression.Expression
import com.shapes.expression.ExpressionResult
import kotlinx.coroutines.flow.Flow

class MainViewModel(
	private val repository: ApplicationRepository = ApplicationRepositoryImplementation()
) : ViewModel() {
	private val executeExpression: ExecuteExpression by lazy { ExecuteExpression() }

	val operation = mutableStateListOf<OperationItem>()
	var operationResult by mutableStateOf<ResultState>(ResultState.Empty)
		private set

	sealed class ResultState {
		data class Value(val result: ExpressionResult) : ResultState()
		object Empty : ResultState()
	}

	var addition by mutableStateOf(false)
	var search by mutableStateOf(false)


	fun getElementList(context: Context): Flow<List<ElementItemData>> {
		return repository.getElementList(context)
	}

	suspend fun addElementItem(
		context: Context,
		elementList: List<ElementItemData>,
		element: ElementItemData
	) {
		val list = listOf(element) + elementList
		repository.setElementList(context, list)
	}

	suspend fun removeElementItem(
		context: Context,
		elementList: List<ElementItemData>,
		element: ElementItemData
	) {
		val list = elementList - element
		repository.setElementList(context, list)
	}

	private fun addOperator(operator: KeyboardOperator = KeyboardOperator.Multiply) {
		val operatorItem = OperatorItem(operator)
		operation.add(operatorItem)
	}

	fun appendOperationItem(operationItem: OperationItem) {
		when (operationItem) {
			is NumberItem -> {
				onNumberItemOperation(operationItem)
			}
			is OperatorItem -> {
				onOperatorItemOperation(operationItem)
			}
			is ElementItem -> {
				onElementItemOperation(operationItem)
			}
		}
	}

	private fun onOperatorItemOperation(operationItem: OperatorItem) {
		operation.add(operationItem)
	}

	private fun onNumberItemOperation(operationItem: NumberItem) {
		if (operation.isNotEmpty()) {
			val previous = operation.last()
			if (trySumItems(previous, operationItem)) {
				return
			}

			if (isAppendable(previous)) {
				addOperator()
			}
		}

		operation.add(operationItem)
	}

	private fun trySumItems(
		previous: OperationItem,
		operationItem: NumberItem
	): Boolean {
		if (previous is NumberItem) {
			val number = previous union operationItem
			number?.let { numberValue ->
				operation.set(operation.lastIndex, NumberItem(numberValue))
			}
		}

		return previous is NumberItem
	}

	private fun isAppendable(operation: OperationItem): Boolean {
		return operation == OperatorItem(KeyboardOperator.Close)
				|| operation is NumberItem
				|| operation is ElementItem
	}

	private fun onElementItemOperation(operationItem: ElementItem) {
		if (operation.isNotEmpty()) {
			val previous = operation.last()
			if (isAppendable(previous)) {
				val keyboardOperator = if (previous is ElementItem) {
					KeyboardOperator.Add
				} else {
					KeyboardOperator.Multiply
				}

				addOperator(keyboardOperator)
			}
		}

		operation.add(operationItem)
	}

	private fun removeLastItem() {
		operation.removeLastOrNull()
	}

	fun onKeyboardButtonClick(keyboardButton: KeyboardButton) {
		clearResultState()
		when (keyboardButton) {
			is NumberButton -> {
				onKeyboardNumberButtonClick(keyboardButton)
			}
			is OperatorButton -> {
				onKeyboardOperatorButtonClick(keyboardButton)
			}
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
		appendOperationItem(item)
	}

	private fun onEqualOperatorButtonClick() {
		if (operation.isNotEmpty()) {
			val expressionValue = makeExpression(operation)
			val expression = Expression(expressionValue)
			val expressionResult = executeExpression(expression)
			operationResult = ResultState.Value(expressionResult)
		}
	}

	private fun onDeleteOperatorButtonClick() {
		removeLastItem()
	}

	private fun onKeyboardNumberButtonClick(keyboardButton: NumberButton) {
		val number = keyboardButton.number
		val item = NumberItem(number.toDouble())
		appendOperationItem(item)
	}

	private fun makeExpression(operationItems: List<OperationItem>): String {
		return operationItems.joinToString(separator = " ") { operationItem ->
			when (operationItem) {
				is ElementItem -> "(${operationItem.elementItem.value})"
				is NumberItem -> operationItem.number
				is OperatorItem -> operationItem.operator.symbol
			}.toString()
		}
	}

	fun onLongKeyboardButtonClick() {
		operation.clear()
		clearResultState()
	}

	private fun clearResultState() {
		operationResult = ResultState.Empty
	}
}
