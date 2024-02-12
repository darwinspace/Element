package com.space.element.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.space.element.domain.model.Element
import com.space.element.domain.model.ExpressionItem
import com.space.element.presentation.main.model.Operator
import com.space.element.presentation.main.model.OperatorType
import com.space.element.util.format
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private val CursorWidth = 3.dp
private val CursorHeight = 32.dp

private val ContentSpace = 4.dp
private val FrameHeight = 96.dp

@Composable
private fun Operator.getColor(): Color {
	return when (type) {
		OperatorType.Arithmetic -> MaterialTheme.colorScheme.primary
		OperatorType.Parentheses -> MaterialTheme.colorScheme.tertiary
	}
}

@Composable
fun ElementExpression(
	expression: () -> SnapshotStateList<ExpressionItem>,
	expressionCursorPosition: () -> Int,
	onExpressionCursorPositionChange: (Int) -> Unit
) {
	val state = rememberLazyListState()
	val scope = rememberCoroutineScope()

	SelectionContainer {
		LazyRow(
			modifier = Modifier
				.fillMaxWidth()
				.height(FrameHeight),
			contentPadding = PaddingValues(
				start = 24.dp - ContentSpace, end = 24.dp
			),
			horizontalArrangement = Arrangement.spacedBy(0.dp),
			verticalAlignment = Alignment.CenterVertically,
			state = state
		) {
			item {
				ExpressionItemSpace(
					cursorVisible = expressionCursorPosition() == 0
				) {
					onExpressionCursorPositionChange(0)
				}
			}

			itemsIndexed(expression()) { index, item ->
				ExpressionItemRow(
					expressionItem = item,
					cursorVisible = expressionCursorPosition() == index + 1
				) {
					onExpressionCursorPositionChange(index + 1)
				}
			}

			scope.launch {
				delay(200.milliseconds)
				state.animateScrollToItem(expressionCursorPosition())
			}
		}
	}
}

@Composable
private fun ExpressionItemRow(
	expressionItem: ExpressionItem,
	cursorVisible: Boolean,
	onSpaceClick: () -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically
	) {
		ExpressionItem(expressionItem)

		ExpressionItemSpace(
			cursorVisible = cursorVisible,
			onClick = onSpaceClick
		)
	}
}

@Composable
private fun ExpressionItemSpace(
	cursorVisible: Boolean,
	onClick: () -> Unit
) {
	Box(
		modifier = Modifier
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null,
				onClick = onClick
			)
			.width(ContentSpace)
			.height(FrameHeight),
		contentAlignment = Alignment.Center,
		content = {
			if (cursorVisible) {
				Cursor()
			}
		}
	)
}

@Composable
private fun ExpressionItem(expressionItem: ExpressionItem) {
	when (expressionItem) {
		is ExpressionItem.ElementItem -> {
			var valueVisible by rememberSaveable(expressionItem) {
				mutableStateOf(false)
			}
			ExpressionElementItem(
				element = expressionItem.element,
				valueVisible = valueVisible,
				onClick = {
					valueVisible = !valueVisible
				}
			)
		}

		is ExpressionItem.OperatorItem -> {
			ExpressionOperatorItem(
				operator = expressionItem.operator
			)
		}

		is ExpressionItem.NumberItem -> {
			ExpressionNumberItem(
				number = expressionItem.number
			)
		}
	}
}

@Composable
private fun ExpressionElementItem(
	element: Element,
	valueVisible: Boolean,
	onClick: () -> Unit
) {
	Surface(
		shape = MaterialTheme.shapes.large,
		color = MaterialTheme.colorScheme.secondaryContainer,
		onClick = onClick
	) {
		Row(modifier = Modifier.padding(12.dp, 8.dp)) {
			ExpressionElementItemName(element.name)

			AnimatedVisibility(visible = valueVisible) {
				ExpressionElementItemValue(element.value)
			}
		}
	}
}

@Composable
private fun ExpressionElementItemName(elementName: String) {
	Text(
		text = elementName,
		style = MaterialTheme.typography.headlineSmall
	)
}

@Composable
fun ExpressionElementItemValue(elementValue: String) {
	Row(verticalAlignment = Alignment.CenterVertically) {
		Spacer(modifier = Modifier.width(12.dp))

		Text(
			text = "= ${elementValue.toDouble().format()}",
			style = MaterialTheme.typography.titleLarge
		)
	}
}

@Composable
private fun ExpressionNumberItem(number: Char) {
	ExpressionItemText(
		text = number.toString()
	)
}

@Composable
private fun ExpressionOperatorItem(operator: Operator) {
	ExpressionItemText(
		text = operator.symbol.toString(),
		color = operator.getColor()
	)
}

@Composable
private fun ExpressionItemText(
	modifier: Modifier = Modifier,
	text: String,
	color: Color = Color.Unspecified
) {
	Text(
		modifier = modifier,
		text = text,
		style = MaterialTheme.typography.displaySmall,
		color = color
	)
}

@Composable
private fun Cursor() {
	val infiniteTransition = rememberInfiniteTransition()
	val alphaValue by infiniteTransition.animateFloat(
		initialValue = 1f,
		targetValue = 0f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 800),
			repeatMode = RepeatMode.Reverse
		)
	)

	Box(
		modifier = Modifier
			.graphicsLayer {
				alpha = alphaValue
			}
			.background(MaterialTheme.colorScheme.primary)
			.width(CursorWidth)
			.height(CursorHeight)
	)
}
