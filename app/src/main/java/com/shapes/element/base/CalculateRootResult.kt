package com.shapes.element.base

class CalculateRootResult(val root: RootElement) {
	private val tokens = "+-*/()".toCharArray()

	/* At this point, the expression must have already been validated. */
	operator fun invoke(): Double {
		/*
		*       Root = 2 * (2 * 20 + (1 * (2 + 1) + 2 * 3))
		*           Root = 2 * 20 + (1 * (2 + 1) + 2 * 3)
		*               Root = 1 * (2 + 1) + 2 * 3
		*                   Root = 2 + 1
		*               Root = 1 * 3 + 2 * 3
		*           Root = 2 * 20 + 9
		*       Root = 2 * 49
		*
		*       Result = 98
		*
		* */
		return 0.0
	}
}
