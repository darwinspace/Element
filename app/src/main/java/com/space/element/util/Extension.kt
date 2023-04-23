package com.space.element.util

import java.text.DecimalFormat

fun Double.format(): String {
	val format = DecimalFormat.getInstance()
	return format.format(this)
}
