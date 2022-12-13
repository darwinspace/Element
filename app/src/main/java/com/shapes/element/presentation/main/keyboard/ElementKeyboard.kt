package com.shapes.element.presentation.main.keyboard

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shapes.element.presentation.main.keyboard.KeyboardButton.NumberButton
import com.shapes.element.presentation.main.keyboard.KeyboardButton.OperatorButton
import com.shapes.element.presentation.main.keyboard.KeyboardOperator.*

object ElementKeyboard {
	private const val compactRows = 6
	private const val mediumRows = 4


	fun getRows(windowSizeClass: WindowSizeClass): List<List<KeyboardButton>> {
		val compact = windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
		val rows = if (compact) {
			compactRows
		} else {
			mediumRows
		}

		return get(compact).chunked(rows)
	}

	private fun get(compact: Boolean = false): List<KeyboardButton> {
		return if (compact) compactButtons else buttons
	}

	fun calculateKeyboardGap(size: WindowSizeClass): Dp {
		return if (size.heightSizeClass == WindowHeightSizeClass.Compact) {
			if (size.widthSizeClass == WindowWidthSizeClass.Compact) {
				2.dp
			} else {
				4.dp
			}
		} else {
			8.dp
		}
	}

	fun calculateKeyboardPadding(size: WindowSizeClass): Dp {
		return if (size.heightSizeClass == WindowHeightSizeClass.Compact) {
			if (size.widthSizeClass == WindowWidthSizeClass.Compact) {
				2.dp
			} else {
				4.dp
			}
		} else {
			12.dp
		}
	}

	private val buttons = listOf(
		OperatorButton(Delete),
		OperatorButton(Open),
		OperatorButton(Close),
		OperatorButton(Divide),
		NumberButton(7),
		NumberButton(8),
		NumberButton(9),
		OperatorButton(Add),
		NumberButton(4),
		NumberButton(5),
		NumberButton(6),
		OperatorButton(Subtract),
		NumberButton(1),
		NumberButton(2),
		NumberButton(3),
		OperatorButton(Multiply),
		NumberButton(0),
		OperatorButton(Equal)
	)

	private val compactButtons = listOf(
		NumberButton(7),
		NumberButton(8),
		NumberButton(9),
		OperatorButton(Open),
		OperatorButton(Close),
		OperatorButton(Delete),
		NumberButton(4),
		NumberButton(5),
		NumberButton(6),
		NumberButton(0),
		OperatorButton(Multiply),
		OperatorButton(Divide),
		NumberButton(1),
		NumberButton(2),
		NumberButton(3),
		OperatorButton(Subtract),
		OperatorButton(Add),
		OperatorButton(Equal)
	)
}
