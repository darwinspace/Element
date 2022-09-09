package com.shapes.element.base

data class RootElement(val expression: String) : Element {
	override fun getResult(): Double {
		val calculateResult = CalculateRootResult(this)
		return calculateResult()
	}
}
