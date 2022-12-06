package com.shapes.element.util

fun <T> List<T>.filterIf(condition: Boolean, predicate: (T) -> Boolean): List<T> {
	return if (condition) {
		filter(predicate)
	} else {
		this
	}
}
