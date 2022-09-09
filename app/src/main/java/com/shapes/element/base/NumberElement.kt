package com.shapes.element.base

data class NumberElement(val number: Number) : Element {
    override fun getResult() = number.toDouble()
}
