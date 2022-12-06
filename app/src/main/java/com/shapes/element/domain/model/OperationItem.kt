package com.shapes.element.domain.model

import com.shapes.element.presentation.main.keyboard.KeyboardOperator

sealed class OperationItem {
	data class ElementItem(val elementItem: ElementItemData) : OperationItem()
	data class OperatorItem(val operator: KeyboardOperator) : OperationItem()
	data class NumberItem(val number: Double) : OperationItem() {
		infix fun union(other: NumberItem): Double? {
			val unionValue = "${number.toInt()}${other.number.toInt()}"
			return unionValue.toDoubleOrNull()
		}
	}
}
