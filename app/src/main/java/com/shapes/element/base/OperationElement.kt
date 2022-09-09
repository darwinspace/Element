package com.shapes.element.base

data class OperationElement(
    val first: Element, val second: Element, val type: Type
) : Element {
    enum class Type {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
    }

    override fun getResult(): Double {
        val firstResult = first.getResult()
        val secondResult = second.getResult()

        return when (type) {
            Type.ADDITION -> firstResult + secondResult
            Type.SUBTRACTION -> firstResult - secondResult
            Type.MULTIPLICATION -> firstResult * secondResult
            Type.DIVISION -> firstResult / secondResult
        }
    }
}