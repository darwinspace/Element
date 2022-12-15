package com.shapes.element.domain.model

//@Serializable
data class Element(val name: String, val value: String) {
	constructor(name: String, value: Double) : this(name, value.toString())
}
