package com.space.element.domain.use_case.expression

import androidx.compose.ui.util.fastJoinToString
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.model.ExpressionResult
import net.objecthunter.exp4j.ExpressionBuilder

class EvaluateExpression {
	operator fun invoke(data: List<ExpressionItem>): ExpressionResult {
		return try {
			val expressionData = data.fastJoinToString(String())
			val expression = ExpressionBuilder(expressionData)
				.build()
			val value = expression.evaluate()
			ExpressionResult.Value(value)
		} catch (exception: Exception) {
			ExpressionResult.Error(exception)
		}
	}
}
