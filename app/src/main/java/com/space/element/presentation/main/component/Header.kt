package com.space.element.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.space.element.domain.model.Element
import com.space.element.domain.model.ExpressionListItem
import com.space.element.domain.model.Function
import com.space.element.domain.model.Operator
import com.space.element.presentation.main.model.ExpressionResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private val ContentSpace = 4.dp
private val ContentHeight = 96.dp

private val CursorWidth = 3.dp
private val CursorHeight = 48.dp

@Composable
fun Header(
	modifier: Modifier = Modifier,
	expression: () -> SnapshotStateList<ExpressionListItem>,
	expressionResultState: () -> ExpressionResultState,
	expressionCursorPosition: () -> Int,
	onExpressionCursorPositionChange: (Int) -> Unit
) {
	Surface(
		modifier = modifier,
		shape = MaterialTheme.shapes.extraLarge.copy(
			topStart = CornerSize(0.dp),
			topEnd = CornerSize(0.dp)
		),
		tonalElevation = 6.dp
	) {
		Column {
			Expression(
				expression = expression,
				expressionCursorPosition = expressionCursorPosition,
				onExpressionCursorPositionChange = onExpressionCursorPositionChange
			)

			ExpressionResult(
				expressionResultState = expressionResultState
			)
		}
	}
}

@Composable
fun Expression(
	expression: () -> SnapshotStateList<ExpressionListItem>,
	expressionCursorPosition: () -> Int,
	onExpressionCursorPositionChange: (Int) -> Unit
) {
	val state = rememberLazyListState()
	val scope = rememberCoroutineScope()

	LazyRow(
		modifier = Modifier
			.fillMaxWidth()
			.height(ContentHeight),
		contentPadding = PaddingValues(
			start = 24.dp - ContentSpace, end = 24.dp
		),
		horizontalArrangement = Arrangement.Start,
		verticalAlignment = Alignment.CenterVertically,
		state = state
	) {
		item {
			ExpressionListItemSpace(
				cursorVisible = expressionCursorPosition() == 0,
				onClick = {
					onExpressionCursorPositionChange(0)
				}
			)
		}

		itemsIndexed(expression()) { index, item ->
			ExpressionListItemRow(
				expressionListItem = item,
				cursorVisible = expressionCursorPosition() == index + 1,
				onSpaceClick = {
					onExpressionCursorPositionChange(index + 1)
				}
			)
		}

		scope.launch {
			delay(200.milliseconds)
			state.animateScrollToItem(expressionCursorPosition())
		}
	}
}

@Composable
private fun ExpressionListItemRow(
	expressionListItem: ExpressionListItem,
	cursorVisible: Boolean,
	onSpaceClick: () -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically
	) {
		ExpressionListItem(expressionListItem)

		ExpressionListItemSpace(
			cursorVisible = cursorVisible,
			onClick = onSpaceClick
		)
	}
}

@Composable
private fun ExpressionListItem(expressionListItem: ExpressionListItem) {
	when (expressionListItem) {
		is ExpressionListItem.ElementItem -> {
			var valueVisible by rememberSaveable(expressionListItem) {
				mutableStateOf(false)
			}
			ExpressionElementItem(
				element = expressionListItem.element,
				valueVisible = valueVisible,
				onClick = {
					valueVisible = !valueVisible
				}
			)
		}

		is ExpressionListItem.OperatorItem -> {
			ExpressionOperatorItem(
				operator = expressionListItem.operator
			)
		}

		is ExpressionListItem.NumberItem -> {
			ExpressionNumberItem(
				number = expressionListItem.number
			)
		}

		is ExpressionListItem.FunctionItem -> {
			ExpressionFunctionItem(
				function = expressionListItem.function
			)
		}
	}
}

@Composable
private fun ExpressionListItemSpace(
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
			.height(ContentHeight),
		contentAlignment = Alignment.Center,
		content = {
			if (cursorVisible) {
				ExpressionCursor()
			}
		}
	)
}

@Composable
private fun ExpressionElementItem(
	element: Element,
	valueVisible: Boolean,
	onClick: () -> Unit
) {
	Surface(
		shape = MaterialTheme.shapes.large,
		color = MaterialTheme.colorScheme.primaryContainer,
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
			text = "= $elementValue",
			style = MaterialTheme.typography.titleLarge,
			fontWeight = FontWeight.Normal
		)
	}
}

@Composable
private fun ExpressionNumberItem(number: Char) {
	Text(
		text = number.toString(),
		style = MaterialTheme.typography.displaySmall,
	)
}

@Composable
private fun ExpressionOperatorItem(operator: Operator) {
	Text(
		text = operator.symbol.toString(),
		color = operator.getColor(),
		style = MaterialTheme.typography.displaySmall,
		fontWeight = FontWeight.Bold
	)
}

@Composable
private fun ExpressionFunctionItem(function: Function) {
	Surface(
		shape = MaterialTheme.shapes.large,
		color = MaterialTheme.colorScheme.tertiaryContainer
	) {
		Text(
			modifier = Modifier.padding(12.dp, 8.dp),
			text = function.name,
			style = MaterialTheme.typography.headlineSmall,
			fontStyle = FontStyle.Italic
		)
	}
}

@Composable
private fun ExpressionCursor() {
	val infiniteTransition = rememberInfiniteTransition(label = "ExpressionCursorAnimation")
	val alphaValue by infiniteTransition.animateFloat(
		initialValue = 1f,
		targetValue = 0f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 800),
			repeatMode = RepeatMode.Reverse
		),
		label = "ExpressionCursorAnimation"
	)

	Box(
		modifier = Modifier
			.graphicsLayer { alpha = alphaValue }
			.background(color = MaterialTheme.colorScheme.primary)
			.size(width = CursorWidth, height = CursorHeight)
	)
}

@Composable
fun ExpressionResult(expressionResultState: () -> ExpressionResultState) {
	val state = expressionResultState()
	if (state is ExpressionResultState.Value) {
		ElementExpressionResultValue(state)
	}
}

@Composable
private fun ElementExpressionResultValue(expressionResultState: ExpressionResultState.Value) {
	val scrollState = rememberScrollState()
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.horizontalScroll(scrollState),
		contentAlignment = Alignment.CenterEnd
	) {
		Text(
			modifier = Modifier.padding(horizontal = 24.dp),
			text = expressionResultState.value.toString(),
			style = MaterialTheme.typography.headlineLarge
		)
	}
}
