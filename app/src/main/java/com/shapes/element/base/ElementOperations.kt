package com.shapes.element.base

import com.shapes.element.base.OperationElement.Type.*

private fun Element.createOperation(other: Element, type: OperationElement.Type) =
    OperationElement(this, other, type)

operator fun Element.plus(other: Element) = createOperation(other, ADDITION)

operator fun Element.minus(other: Element) = createOperation(other, SUBTRACTION)

operator fun Element.times(other: Element) = createOperation(other, MULTIPLICATION)

operator fun Element.div(other: Element) = createOperation(other, DIVISION)
