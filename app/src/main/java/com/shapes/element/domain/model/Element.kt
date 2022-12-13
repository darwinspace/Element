package com.shapes.element.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Element(val name: String, val value: Double) {
	constructor(name: String, value: String) : this(name, value.toDoubleOrNull() ?: 0.0)
}
