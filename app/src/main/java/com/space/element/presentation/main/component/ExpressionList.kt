package com.space.element.presentation.main.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.domain.model.Element
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.MainViewModel
import com.space.element.presentation.main.model.KeyboardButton
import com.space.element.presentation.main.model.KeyboardButtonType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private val CursorWidth = 2.dp
private val CursorHeight = 28.dp

private val StartContentSpace = 4.dp
private val ContentSpace = 8.dp
private val FrameHeight = 96.dp

@Composable
fun ExpressionList(modifier: Modifier) {
	val viewModel: MainViewModel = viewModel()
	val expression = viewModel.expression
	val expressionCursor = viewModel.expressionCursor

	val infiniteTransition = rememberInfiniteTransition()
	val cursorColorPrimary = MaterialTheme.colorScheme.primary
	val cursorColor by infiniteTransition.animateColor(
		initialValue = cursorColorPrimary.copy(alpha = 0f),
		targetValue = cursorColorPrimary,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 500, easing = LinearEasing),
			repeatMode = RepeatMode.Reverse
		)
	)

	val state = rememberLazyListState()
	val scope = rememberCoroutineScope()

	val getItemSpacerWidth: (Int) -> Dp = { index ->
		val isNumberItem: (ExpressionItem?) -> Boolean = {
			(it is ExpressionItem.OperatorItem && it.keyboardButton.type == KeyboardButtonType.Number)
		}

		val currentIsNumberItem = isNumberItem(expression[index])
		val nextIsNumberItem = isNumberItem(expression.getOrNull(index + 1))

		if (currentIsNumberItem && nextIsNumberItem) {
			CursorWidth
		} else {
			ContentSpace
		}
	}

	Surface {
		LazyRow(
			modifier = modifier.height(FrameHeight),
			contentPadding = PaddingValues(
				start = 24.dp - StartContentSpace, end = 24.dp
			),
			horizontalArrangement = Arrangement.spacedBy(0.dp),
			verticalAlignment = Alignment.CenterVertically,
			state = state
		) {
			item {
				ExpressionItemSpacer(
					width = StartContentSpace,
					cursorVisible = expression.isEmpty() || expressionCursor == 0,
					cursorColor = cursorColor,
					onClick = {
						viewModel.onExpressionSpaceClick(0)
					}
				)
			}

			itemsIndexed(
				items = expression,
				key = { _, item ->
					when (item) {
						is ExpressionItem.ElementItem -> item.element.name
						is ExpressionItem.OperatorItem -> item.keyboardButton.symbol
					}
				}
			) { index, item ->
				ExpressionItemRow(
					item = item,
					index = index,
					width = getItemSpacerWidth(index),
					cursorColor = cursorColor,
					cursorVisible = expressionCursor == index + 1,
				)
			}

			scope.launch {
				delay(200.milliseconds)
				state.animateScrollToItem(expressionCursor)
			}
		}
	}
}

@Composable
private fun ExpressionItemRow(
	item: ExpressionItem,
	index: Int,
	width: Dp,
	cursorColor: Color,
	cursorVisible: Boolean
) {
	val viewModel: MainViewModel = viewModel()
	Row(
		verticalAlignment = Alignment.CenterVertically
	) {
		ExpressionItem(item)

		ExpressionItemSpacer(
			width = width,
			cursorVisible = cursorVisible,
			cursorColor = cursorColor,
			onClick = {
				viewModel.onExpressionSpaceClick(index)
			}
		)
	}
}

@Composable
private fun ExpressionItemSpacer(
	width: Dp,
	cursorVisible: Boolean,
	cursorColor: Color,
	onClick: () -> Unit
) {
	Box(
		modifier = Modifier
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null,
				onClick = onClick
			)
			.width(width)
			.height(FrameHeight),
		contentAlignment = Alignment.Center,
		content = {
			if (cursorVisible) {
				Cursor(cursorColor)
			}
		}
	)
}

@Composable
private fun ExpressionItem(item: ExpressionItem) {
	when (item) {
		is ExpressionItem.ElementItem -> {
			var visible by rememberSaveable { mutableStateOf(true) }
			ExpressionElementItem(
				element = item.element,
				visible = visible,
				onClick = { visible = !visible }
			)
		}
		is ExpressionItem.OperatorItem -> {
			ExpressionOperatorItem(item.keyboardButton)
		}
	}
}

@Composable
private fun ExpressionElementItem(
	element: Element,
	visible: Boolean = true,
	onClick: () -> Unit
) {
	val shape = MaterialTheme.shapes.small
	val padding = PaddingValues(12.dp, 8.dp)

	Surface(
		modifier = Modifier
			.clip(shape)
			.clickable(
				role = Role.Button,
				onClick = onClick
			),
		shape = shape,
		tonalElevation = 5.dp
	) {
		Box(modifier = Modifier.padding(padding)) {
			val text = if (visible) element.name else element.value.format()
			ExpressionElementItemName(text)
		}
	}
}

@Composable
private fun ExpressionElementItemName(elementName: String) {
	val style: TextStyle = MaterialTheme.typography.titleSmall
	Text(text = elementName, style = style)
}

@Composable
private fun ExpressionOperatorItem(keyboardButton: KeyboardButton) {
	val color = when (keyboardButton.type) {
		KeyboardButtonType.Operator -> {
			MaterialTheme.colorScheme.tertiary
		}
		KeyboardButtonType.Parentheses -> {
			MaterialTheme.colorScheme.primary
		}
		KeyboardButtonType.Number -> {
			MaterialTheme.colorScheme.onSurface
		}
		KeyboardButtonType.Function -> TODO()
		KeyboardButtonType.Delete,
		KeyboardButtonType.Equal -> {
			throw IllegalStateException("KeyboardButtonType.Delete and KeyboardButtonType.Equal are not valid expression operator items")
		}
	}

	OperatorExpressionItemText(keyboardButton = keyboardButton, color = color)
}

@Composable
private fun OperatorExpressionItemText(
	modifier: Modifier = Modifier,
	keyboardButton: KeyboardButton,
	color: Color = Color.Unspecified
) {
	Text(
		modifier = modifier,
		text = keyboardButton.symbol,
		style = MaterialTheme.typography.titleMedium,
		color = color
	)
}

@Composable
private fun Cursor(color: Color = MaterialTheme.colorScheme.primary) {
	Box(
		modifier = Modifier
			.background(color)
			.width(CursorWidth)
			.height(CursorHeight)
	)
}
