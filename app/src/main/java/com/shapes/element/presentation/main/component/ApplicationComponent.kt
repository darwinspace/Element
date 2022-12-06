package com.shapes.element.presentation.main.component

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shapes.element.R
import com.shapes.element.domain.model.ElementItemData
import com.shapes.element.domain.model.OperationItem
import com.shapes.element.domain.model.OperationItem.*
import com.shapes.element.presentation.main.keyboard.KeyboardButton
import com.shapes.element.presentation.main.keyboard.KeyboardData
import com.shapes.element.presentation.main.keyboard.KeyboardOperator
import com.shapes.element.presentation.main.viewmodel.MainViewModel
import com.shapes.element.ui.theme.ElementTheme
import com.shapes.element.ui.theme.space
import com.shapes.element.util.filterIf
import com.shapes.expression.ExpressionResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ApplicationComponent(size: WindowSizeClass) {
	if (size.widthSizeClass == WindowWidthSizeClass.Compact) {
		VerticalApplicationComponent(size)
	} else {
		HorizontalApplicationComponent(size)
	}
}

@Composable
fun HorizontalApplicationComponent(size: WindowSizeClass) {
	Surface(color = MaterialTheme.colorScheme.background) {
		Row(modifier = Modifier.fillMaxSize()) {
			ElementList(
				modifier = Modifier
					.fillMaxHeight()
					.weight(4f)
			)

			MainApplicationContent(
				modifier = Modifier
					.fillMaxHeight()
					.weight(6f),
				size = size
			)
		}
	}
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun VerticalApplicationComponent(size: WindowSizeClass) {
	val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
	val bottomSheetState = bottomSheetScaffoldState.bottomSheetState
	val scope = rememberCoroutineScope()

	val isCompactHeight = size.heightSizeClass == WindowHeightSizeClass.Compact

	val bottomSheetButtonHeight = if (isCompactHeight) 32.dp else 48.dp
	val bottomSheetHeight = 352.dp

	BottomSheetScaffold(
		scaffoldState = bottomSheetScaffoldState,
		sheetContent = {
			ApplicationComponentMediumHeightBottomSheetButton(
				bottomSheetState,
				bottomSheetButtonHeight
			) {
				scope.launch {
					if (bottomSheetState.isCollapsed) {
						bottomSheetState.expand()
					} else {
						bottomSheetState.collapse()
					}
				}
			}
			ElementList(
				modifier = Modifier
					.height(bottomSheetHeight)
					.fillMaxWidth()
			)
		},
		sheetShape = RectangleShape,
		sheetElevation = 0.dp,
		sheetPeekHeight = bottomSheetButtonHeight
	) { contentPadding ->
		Surface(
			modifier = Modifier.padding(contentPadding),
			color = MaterialTheme.colorScheme.background
		) {
			MainApplicationContent(
				modifier = Modifier.fillMaxSize(),
				size = size
			)
		}
	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ApplicationComponentMediumHeightBottomSheetButton(
	bottomSheetState: BottomSheetState,
	height: Dp,
	onClick: () -> Unit
) {
	val degrees by animateFloatAsState(
		targetValue = if (bottomSheetState.isCollapsed) 0f else 180f
	)

	Surface(
		modifier = Modifier.clickable(role = Role.Button, onClick = onClick),
		tonalElevation = 3.dp
	) {
		Box(
			modifier = Modifier
				.height(height)
				.fillMaxWidth(),
			contentAlignment = Alignment.Center
		) {
			Icon(
				modifier = Modifier.rotate(degrees),
				imageVector = Icons.Default.KeyboardArrowUp,
				contentDescription = null
			)
		}
	}
}

@Composable
private fun MainApplicationContent(
	modifier: Modifier = Modifier,
	size: WindowSizeClass
) {
	Column(modifier = modifier) {
		OperationFrame(modifier = Modifier.weight(1f))
		Keyboard(size)
	}
}

@Composable
private fun OperationFrame(modifier: Modifier = Modifier) {
	val scrollState = rememberScrollState()
	Surface(
		modifier = modifier.verticalScroll(scrollState)
	) {
		Column {
			OperationList()
			ResultFrame()
		}
	}
}

fun Double.format(): String {
	val format = java.text.DecimalFormat.getInstance()
	return format.format(this)
}

@Composable
fun GenericOperationElementItem(operationItem: OperationItem) {
	when (operationItem) {
		is NumberItem -> {
			val text = operationItem.number.format()
			ElementItemText(text = text)
		}
		is OperatorItem -> {
			val operator = operationItem.operator
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
fun OperationList(
	viewModel: MainViewModel = viewModel()
) {
	val operations = viewModel.operation
	val infiniteTransition = rememberInfiniteTransition()
	val opacityValue by infiniteTransition.animateFloat(
		initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 600, easing = LinearEasing),
			repeatMode = RepeatMode.Reverse
		)
	)
	val state = rememberLazyListState()
	val scope = rememberCoroutineScope()
	Surface {
		LazyRow(
			modifier = Modifier
				.height(96.dp)
				.fillMaxWidth(),
			state = state,
			contentPadding = PaddingValues(horizontal = 24.dp),
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			items(operations) { elementItem ->
				if (elementItem is ElementItem) {
					var visible by rememberSaveable { mutableStateOf(true) }
					OperationElementItem(
						elementItem = elementItem.elementItem,
						visible = visible,
						onClick = { visible = !visible }
					)
				} else {
					GenericOperationElementItem(elementItem)
				}
			}

			item {
				Cursor(opacityValue = opacityValue)
			}

			scope.launch {
				delay(100)
				state.animateScrollToItem(operations.size)
			}
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.Cursor(
	width: Dp = 4.dp,
	height: Dp = 32.dp,
	opacityValue: Float
) {
	val cursorColor = MaterialTheme.colorScheme.primary
	Box(
		modifier = Modifier
			.animateItemPlacement()
			.alpha(opacityValue)
			.background(cursorColor)
			.size(width, height)
	)
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
		color = color,
		letterSpacing = 1.sp
	)
}

@Composable
private fun ResultFrame(viewModel: MainViewModel = viewModel()) {
	when (val resultState = viewModel.operationResult) {
		is MainViewModel.ResultState.Value -> {
			when (val expressionResult = resultState.result) {
				is ExpressionResult.Value -> {
					val scrollState = rememberScrollState()
					val resultValue = expressionResult.value.format()
					Surface(
						modifier = Modifier
							.fillMaxWidth()
							.horizontalScroll(
								state = scrollState
							)
					) {
						Box(
							modifier = Modifier.padding(horizontal = 32.dp),
							contentAlignment = Alignment.CenterEnd
						) {
							ResultText(resultValue)
						}
					}
				}
				is ExpressionResult.ExpressionException -> {
					Surface(
						modifier = Modifier
							.padding(bottom = 24.dp)
							.padding(horizontal = 24.dp)
							.fillMaxWidth(),
						shape = MaterialTheme.shapes.medium,
						color = MaterialTheme.colorScheme.errorContainer
					) {
						val text = if (expressionResult.exception is ArithmeticException) {
							stringResource(id = R.string.exception_arithmetic)
						} else {
							stringResource(id = R.string.exception_expression)
						}

						Text(
							modifier = Modifier
								.fillMaxWidth()
								.padding(24.dp),
							text = text,
							textAlign = TextAlign.End,
							style = MaterialTheme.typography.headlineSmall
						)
					}
				}
			}
		}
		MainViewModel.ResultState.Empty -> Unit
	}
}

@Composable
fun ResultText(text: String) {
	val style = MaterialTheme.typography.displayMedium
	Text(
		text = text,
		textAlign = TextAlign.End,
		style = style
	)
}

@Composable
fun OperationElementItem(
	elementItem: ElementItemData,
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
			val text = if (visible) elementItem.name else elementItem.value.format()
			OperationElementItemName(text)
		}
	}
}

@Composable
fun OperationElementItemName(elementName: String) {
	val style: TextStyle = MaterialTheme.typography.titleSmall
	Text(text = elementName, style = style)
}

enum class KeyboardButtonType {
	Default, Operator, Delete, Parentheses, Equal
}

fun getKeyboardButtonType(keyboardButton: KeyboardButton): KeyboardButtonType {
	return when (keyboardButton) {
		is KeyboardButton.NumberButton -> KeyboardButtonType.Default
		is KeyboardButton.OperatorButton -> {
			when (keyboardButton.operator) {
				KeyboardOperator.Delete -> KeyboardButtonType.Delete
				KeyboardOperator.Open, KeyboardOperator.Close -> KeyboardButtonType.Parentheses
				KeyboardOperator.Multiply,
				KeyboardOperator.Divide,
				KeyboardOperator.Add,
				KeyboardOperator.Subtract -> KeyboardButtonType.Operator
				KeyboardOperator.Equal -> KeyboardButtonType.Equal
			}
		}
	}
}

@Composable
fun Keyboard(
	size: WindowSizeClass,
	viewModel: MainViewModel = viewModel()
) {
	val keyboardRows = KeyboardData(size)

	val isHeightCompact = size.heightSizeClass == WindowHeightSizeClass.Compact
	val isWidthCompact = size.widthSizeClass == WindowWidthSizeClass.Compact
	val (keyboardGap, keyboardPadding) = if (isHeightCompact) {
		if (isWidthCompact) {
			2.dp to 2.dp
		} else {
			6.dp to 6.dp
		}
	} else {
		8.dp to 12.dp
	}

	Surface(color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)) {
		Column(
			modifier = Modifier.padding(keyboardPadding),
			verticalArrangement = Arrangement.spacedBy(0.dp)
		) {
			keyboardRows.forEach { row ->
				Row {
					row.forEach { keyboardButton ->
						val buttonType = getKeyboardButtonType(keyboardButton)
						val isDelete = buttonType == KeyboardButtonType.Delete
						KeyboardButton(
							modifier = Modifier
								.weight(1f)
								.padding(keyboardGap),
							buttonType = buttonType,
							onLongClick = {
								if (isDelete) {
									viewModel.onLongKeyboardButtonClick()
								}
							},
							onClick = {
								viewModel.onKeyboardButtonClick(keyboardButton)
							}
						) {
							KeyboardButtonText(keyboardButton.text)
						}
					}
				}
			}
		}
	}
}

@Composable
fun KeyboardButtonText(text: String) {
	Text(text = text, style = MaterialTheme.typography.labelLarge)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KeyboardButton(
	modifier: Modifier = Modifier,
	shape: Shape = MaterialTheme.shapes.medium,
	buttonType: KeyboardButtonType = KeyboardButtonType.Default,
	onLongClick: (() -> Unit)? = null,
	onClick: () -> Unit,
	content: @Composable BoxScope.() -> Unit
) {
	val color = when (buttonType) {
		KeyboardButtonType.Delete -> MaterialTheme.colorScheme.tertiaryContainer
		KeyboardButtonType.Parentheses,
		KeyboardButtonType.Operator -> MaterialTheme.colorScheme.secondaryContainer
		KeyboardButtonType.Equal -> MaterialTheme.colorScheme.primary
		KeyboardButtonType.Default -> MaterialTheme.colorScheme.surface
	}

	val tonalElevation =
		if (isSystemInDarkTheme() && buttonType == KeyboardButtonType.Default) 2.dp else 0.dp

	Surface(
		modifier = modifier
			.clip(shape)
			.combinedClickable(
				role = Role.Button,
				onLongClick = onLongClick,
				onClick = onClick
			),
		shape = shape,
		color = color,
		tonalElevation = tonalElevation
	) {
		Box(
			modifier = Modifier
				.height(48.dp)
				.padding(horizontal = 16.dp),
			contentAlignment = Alignment.Center,
			content = content
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementList(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
	val space = MaterialTheme.space.regular

	val focusManager = LocalFocusManager.current

	var search by rememberSaveable(viewModel.search) { mutableStateOf(String()) }

	var name by rememberSaveable(viewModel.addition) { mutableStateOf(String()) }
	var value by rememberSaveable(viewModel.addition) { mutableStateOf(String()) }

	val isAdditionButtonEnabled = name.isNotBlank()

	val scope = rememberCoroutineScope()
	val context = LocalContext.current
	val elementList by viewModel.getElementList(context).collectAsState(initial = emptyList())

	Surface(modifier = modifier, tonalElevation = 2.dp) {
		LazyColumn(
			contentPadding = PaddingValues(space),
			verticalArrangement = Arrangement.spacedBy(space)
		) {
			item {
				Column {
					Row(
						modifier = Modifier.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically
					) {
						val isAdditionVisible = viewModel.addition
						val isSearchVisible = elementList.isNotEmpty()

						AnimatedVisibility(visible = isSearchVisible || isAdditionVisible) {
							androidx.compose.animation.AnimatedVisibility(
								visible = isSearchVisible && !isAdditionVisible,
								enter = fadeIn(),
								exit = fadeOut()
							) {
								FilledTonalIconToggleButton(
									modifier = Modifier.size(48.dp),
									checked = viewModel.search,
									onCheckedChange = { viewModel.search = it }
								) {
									androidx.compose.animation.AnimatedVisibility(
										visible = viewModel.search,
										enter = fadeIn(),
										exit = fadeOut()
									) {
										Icon(
											imageVector = Icons.Default.Close,
											contentDescription = null
										)
									}

									androidx.compose.animation.AnimatedVisibility(
										visible = !viewModel.search,
										enter = fadeIn(),
										exit = fadeOut()
									) {
										Icon(
											imageVector = Icons.Default.Search,
											contentDescription = null
										)
									}
								}
							}

							androidx.compose.animation.AnimatedVisibility(
								visible = isAdditionVisible,
								enter = fadeIn(),
								exit = fadeOut()
							) {
								FilledTonalIconButton(
									modifier = Modifier.size(48.dp),
									onClick = {
										viewModel.addition = !viewModel.addition
									}
								) {
									Icon(
										imageVector = Icons.Default.Close,
										contentDescription = null
									)
								}
							}
						}

						AnimatedVisibility(
							visible = !(!isSearchVisible && !isAdditionVisible)
						) {
							Spacer(modifier = Modifier.width(space))
						}

						val noneName = elementList.none { it.name == name.trim() }
						val isAddItemButtonEnabled =
							if (viewModel.addition) isAdditionButtonEnabled && noneName else true

						Box(
							modifier = Modifier
								.weight(1f)
								.height(60.dp),
							contentAlignment = Alignment.Center
						) {
							androidx.compose.animation.AnimatedVisibility(
								visible = !viewModel.search,
								enter = fadeIn(),
								exit = fadeOut()
							) {
								Button(
									enabled = isAddItemButtonEnabled,
									modifier = Modifier
										.fillParentMaxWidth()
										.height(48.dp),
									shape = MaterialTheme.shapes.small,
									onClick = {
										scope.launch {
											if (viewModel.addition && noneName) {
												val element = ElementItemData(name.trim(), value)
												viewModel.addElementItem(
													context,
													elementList,
													element
												)
											}

											viewModel.addition = !viewModel.addition
										}
									}
								) {
									Text(text = stringResource(id = R.string.element_add))
								}
							}

							androidx.compose.animation.AnimatedVisibility(
								visible = viewModel.search,
								enter = fadeIn(),
								exit = fadeOut()
							) {
								OutlinedTextField(
									modifier = Modifier.fillParentMaxWidth(),
									shape = MaterialTheme.shapes.small,
									singleLine = true,
									keyboardOptions = KeyboardOptions(
										capitalization = KeyboardCapitalization.Sentences,
										imeAction = ImeAction.Search
									),
									keyboardActions = KeyboardActions(
										onSearch = {
											focusManager.clearFocus()
										}
									),
									value = search,
									onValueChange = {
										search = it
									},
									placeholder = {
										Text(text = stringResource(id = R.string.search))
									},
									trailingIcon = {
										Icon(
											imageVector = Icons.Default.Search,
											contentDescription = null
										)
									}
								)
							}
						}
					}

					AnimatedVisibility(visible = viewModel.addition) {
						Column {
							Spacer(modifier = Modifier.height(space))

							OutlinedTextField(
								modifier = Modifier.fillMaxWidth(),
								shape = MaterialTheme.shapes.small,
								keyboardOptions = KeyboardOptions(
									capitalization = KeyboardCapitalization.Sentences,
									imeAction = ImeAction.Next
								),
								keyboardActions = KeyboardActions(
									onNext = {
										focusManager.moveFocus(FocusDirection.Next)
										name = name.trim()
									}
								),
								singleLine = true,
								value = name,
								onValueChange = { name = it },
								placeholder = {
									Text(text = stringResource(id = R.string.element_name))
								}
							)

							Spacer(modifier = Modifier.height(space))

							OutlinedTextField(
								modifier = Modifier.fillMaxWidth(),
								shape = MaterialTheme.shapes.small,
								keyboardOptions = KeyboardOptions(
									keyboardType = KeyboardType.Decimal,
									imeAction = ImeAction.Done
								),
								keyboardActions = KeyboardActions(
									onDone = {
										focusManager.clearFocus()
									}
								),
								singleLine = true,
								value = value,
								onValueChange = { value = it },
								placeholder = {
									Text(text = stringResource(id = R.string.element_value))
								}
							)
						}
					}
				}
			}

			keyboardContent(viewModel, elementList, search)
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.keyboardContent(
	viewModel: MainViewModel,
	elementList: List<ElementItemData>,
	search: String
) {
	val filterEnabled = viewModel.search && search.isNotBlank()
	val filteredElements = elementList.filterIf(filterEnabled) {
		it.name.contains(search, ignoreCase = true)
	}

	items(filteredElements) { item ->
		val space = MaterialTheme.space.regular
		val shape = MaterialTheme.shapes.medium
		val textStyle = MaterialTheme.typography.titleSmall
		val name = item.name
		val value = item.value.format()

		val scope = rememberCoroutineScope()

		val context = LocalContext.current

		Surface(
			modifier = Modifier
				.clip(shape)
				.combinedClickable(
					role = Role.Button,
					onClick = {
						val operationItem = ElementItem(item)
						viewModel.appendOperationItem(operationItem)
					},
					onLongClick = {
						scope.launch {
							viewModel.removeElementItem(context, elementList, item)
						}
					}
				),
			shape = shape,
			tonalElevation = 3.dp
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(space),
				horizontalArrangement = Arrangement.spacedBy(24.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					modifier = Modifier.weight(1f),
					text = name,
					textAlign = TextAlign.Justify,
					style = textStyle
				)
				Text(text = value, style = textStyle)
			}
		}
	}

	if (elementList.isEmpty()) {
		item {
			ElementListEmptyCard()
		}
	}

	if (elementList.isNotEmpty() && filteredElements.isEmpty()) {
		item {
			Surface(tonalElevation = 2.dp, shape = MaterialTheme.shapes.small) {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(24.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.spacedBy(16.dp)
				) {
					Icon(imageVector = Icons.Default.Search, contentDescription = null)
					Text(
						text = stringResource(id = R.string.element_list_search_empty),
						style = MaterialTheme.typography.bodyLarge,
						textAlign = TextAlign.Center
					)
				}
			}
		}
	}
}

@Composable
private fun ElementListEmptyCard() {
	val space = 32.dp
	Surface(
		shape = MaterialTheme.shapes.medium,
		tonalElevation = 3.dp
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(space),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Text(
				text = stringResource(id = R.string.element_list_empty),
				style = MaterialTheme.typography.titleSmall,
				textAlign = TextAlign.Center
			)

			Text(
				text = stringResource(id = R.string.element_list_empty_content),
				style = MaterialTheme.typography.bodyLarge,
				textAlign = TextAlign.Center
			)
		}
	}
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(widthDp = 640, heightDp = 360)
@Composable
fun ApplicationPreview() {
	ElementTheme {
		ApplicationComponent(WindowSizeClass.calculateFromSize(DpSize(640.dp, 360.dp)))
	}
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(widthDp = 360, heightDp = 640)
@Composable
fun HorizontalApplicationPreview() {
	ElementTheme {
		ApplicationComponent(WindowSizeClass.calculateFromSize(DpSize(360.dp, 640.dp)))
	}
}
