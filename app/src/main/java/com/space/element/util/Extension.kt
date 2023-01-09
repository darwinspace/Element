package com.space.element.util

import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.model.Expression
import java.text.DecimalFormat

fun Double.format(): String {
	val format = DecimalFormat.getInstance()
	return format.format(this)
}

fun List<ExpressionItem>.toExpression(): Expression {
	return Expression(joinToString(""))
}

inline fun <T> List<T>.filterIf(condition: Boolean, predicate: (T) -> Boolean): List<T> {
	return if (condition) filter(predicate) else this
}
