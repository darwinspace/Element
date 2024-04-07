package com.space.element.domain.use_case.expression

import androidx.compose.ui.util.fastJoinToString
import com.space.element.domain.model.ExpressionListItem
import com.space.element.presentation.main.model.ExpressionResultState
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function

class EvaluateExpression {
	operator fun invoke(data: List<ExpressionListItem>): ExpressionResultState {
		return try {
			val functions = data.filterIsInstance<ExpressionListItem.FunctionItem>().map { (expressionFunction) ->
				object : Function(expressionFunction.name) {
					override fun apply(vararg args: Double): Double {
						val argument = args[0]
						val expression = ExpressionBuilder(expressionFunction.definition)
							.variable("x")
							.build()
							.setVariable("x", argument)
						return expression.evaluate()
					}
				}
			}

			val expressionData = data.fastJoinToString(String())
			val expression = ExpressionBuilder(expressionData)
				.functions(functions)
				.build()
			val value = expression.evaluate()
			ExpressionResultState.Value(value)
		} catch (exception: Exception) {
			ExpressionResultState.Error(exception)
		}
	}
}
