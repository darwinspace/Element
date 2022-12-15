package com.shapes.element.presentation.main.component

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shapes.element.domain.model.Element
import com.shapes.element.domain.model.ExpressionItem
import com.shapes.element.presentation.main.keyboard.KeyboardOperator
import com.shapes.element.presentation.main.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val CursorWidth = 2.dp
private val CursorHeight = 28.dp

private val StartContentSpace = 4.dp
private val ContentSpace = 8.dp
private val FrameHeight = 96.dp

@Composable
fun ExpressionList(modifier: Modifier, viewModel: MainViewModel = viewModel()) {
	val expression = viewModel.expression
	val cursor = viewModel.cursor

	val infiniteTransition = rememberInfiniteTransition()
	val cursorAlpha by infiniteTransition.animateFloat(
		initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 600, easing = LinearEasing),
			repeatMode = RepeatMode.Reverse
		)
	)
	val state = rememberLazyListState()
	val scope = rememberCoroutineScope()

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
				Box(
					modifier = Modifier
						.clickable(
							interactionSource = remember { MutableInteractionSource() },
							indication = null,
							enabled = cursor != 0
						) {
							viewModel.cursor = 0
						}
						.width(StartContentSpace)
						.height(FrameHeight),
					contentAlignment = Alignment.Center
				) {
					if (expression.isEmpty() || cursor == 0) {
						Cursor(cursorAlpha)
					}
				}
			}

			itemsIndexed(expression) { index, item ->
				Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					if (item is ExpressionItem.ElementItem) {
						var visible by rememberSaveable { mutableStateOf(true) }
						ExpressionElementItem(
							element = item.element,
							visible = visible,
							onClick = { visible = !visible }
						)
					} else {
						GenericExpressionItem(item)
					}

					val areNumberItem = item is ExpressionItem.NumberItem &&
							expression.getOrNull(index + 1) is ExpressionItem.NumberItem

					val width = if (areNumberItem) {
						CursorWidth
					} else {
						ContentSpace
					}

					Box(
						modifier = Modifier
							.clickable(
								interactionSource = remember { MutableInteractionSource() },
								indication = null
							) {
								viewModel.cursor = index + 1
							}
							.width(width)
							.height(FrameHeight),
						contentAlignment = Alignment.Center
					) {
						if (cursor == index + 1) {
							Cursor(cursorAlpha)
						}
					}
				}
			}

			scope.launch {
				delay(100L)
				state.animateScrollToItem(expression.size)
			}
		}
	}
}


@Composable
fun ExpressionElementItem(
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
			OperationElementItemName(text)
		}
	}
}

@Composable
fun OperationElementItemName(elementName: String) {
	val style: TextStyle = MaterialTheme.typography.titleSmall
	Text(text = elementName, style = style)
}

@Composable
fun GenericExpressionItem(expressionItem: ExpressionItem) {
	when (expressionItem) {
		is ExpressionItem.NumberItem -> {
			ElementItemText(text = expressionItem.number)
		}
		is ExpressionItem.OperatorItem -> {
			val operator = expressionItem.operator
			val isParentheses = operator == KeyboardOperator.Open
					|| operator == KeyboardOperator.Close
			val color = if (isParentheses) {
				MaterialTheme.colorScheme.tertiary
			} else {
				MaterialTheme.colorScheme.primary
			}
			ElementItemText(text = operator.symbol, color = color)
		}
		else -> Unit
	}
}

@Composable
fun ElementItemText(
	modifier: Modifier = Modifier,
	text: String,
	color: Color = Color.Unspecified
) {
	Text(
		modifier = modifier,
		text = text,
		style = MaterialTheme.typography.titleMedium,
		color = color
	)
}

@Composable
fun Cursor(alpha: Float = 1f) {
	val color: Color = MaterialTheme.colorScheme.primary
	Box(
		modifier = Modifier
			.alpha(alpha)
			.background(color)
			.width(CursorWidth)
			.height(CursorHeight)
	)
}
