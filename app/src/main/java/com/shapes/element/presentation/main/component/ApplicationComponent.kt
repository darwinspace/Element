package com.shapes.element.presentation.main.component

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shapes.element.R
import com.shapes.element.domain.model.Element
import com.shapes.element.domain.model.ExpressionItem.*
import com.shapes.element.presentation.main.keyboard.ElementKeyboard
import com.shapes.element.presentation.main.keyboard.KeyboardButton
import com.shapes.element.presentation.main.viewmodel.ExpressionResultState
import com.shapes.element.presentation.main.viewmodel.MainViewModel
import com.shapes.element.ui.theme.space
import com.shapes.element.util.filterIf
import com.shapes.expression.ExpressionResult
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

			VerticalDivider(
				color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
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
private fun VerticalDivider(
	color: Color = MaterialTheme.colorScheme.primaryContainer,
	thickness: Dp = DividerDefaults.Thickness
) {
	Box(
		modifier = Modifier
			.fillMaxHeight()
			.width(thickness)
			.background(color = color)
	)
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

	val tonalElevation = 1.dp

	Surface(
		modifier = Modifier.clickable(role = Role.Button, onClick = onClick),
		tonalElevation = tonalElevation
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
		ExpressionData(modifier = Modifier.weight(1f))
		Keyboard(size)
	}
}

@Composable
private fun ExpressionData(modifier: Modifier = Modifier) {
	val scrollState = rememberScrollState()
	val tonalElevation = 3.dp

	Surface(
		modifier = Modifier
			.verticalScroll(scrollState)
			.then(modifier),
		tonalElevation = tonalElevation
	) {
		Column {
			ExpressionList(modifier = Modifier.fillMaxWidth())
			ResultFrame()
		}
	}
}

fun Double.format(): String {
	val format = java.text.DecimalFormat.getInstance()
	return format.format(this)
}

@Composable
private fun ResultFrame(viewModel: MainViewModel = viewModel()) {
	val resultState = viewModel.result
	if (resultState is ExpressionResultState.Value) {
		when (val result = resultState.result) {
			is ExpressionResult.Value -> {
				ExpressionResult(
					modifier = Modifier
						.fillMaxWidth()
						.horizontalScroll(state = rememberScrollState()),
					result = result
				)
			}
			is ExpressionResult.ExpressionException -> {
				ExpressionException(result)
			}
		}
	}
}

@Composable
private fun ExpressionException(result: ExpressionResult.ExpressionException) {
	Surface(
		modifier = Modifier
			.padding(bottom = 24.dp)
			.padding(horizontal = 24.dp)
			.fillMaxWidth(),
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.errorContainer
	) {
		val text = if (result.exception is ArithmeticException) {
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

@Composable
private fun ExpressionResult(modifier: Modifier, result: ExpressionResult.Value) {
	val value = result.value.format()
	Surface(modifier = modifier) {
		Box(
			modifier = Modifier.padding(32.dp),
			contentAlignment = Alignment.CenterEnd
		) {
			ResultText(value)
		}
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
fun Keyboard(size: WindowSizeClass) {
	val keyboardPadding = ElementKeyboard.calculateKeyboardPadding(size)

	Surface {
		Column(modifier = Modifier.padding(keyboardPadding)) {
			KeyboardRows(size)
		}
	}
}

@Composable
private fun KeyboardRows(size: WindowSizeClass) {
	val rows = ElementKeyboard.getRows(size)
	rows.forEach { row ->
		Row {
			KeyboardButtons(size, row)
		}
	}
}

@Composable
private fun RowScope.KeyboardButtons(
	size: WindowSizeClass,
	rows: List<KeyboardButton>,
) {
	val viewModel: MainViewModel = viewModel()
	val keyboardGap = ElementKeyboard.calculateKeyboardGap(size)

	rows.forEach { keyboardButton ->
		KeyboardButton(
			modifier = Modifier
				.weight(1f)
				.padding(keyboardGap),
			keyboardButton = keyboardButton,
			onLongClick = {
				viewModel.onLongKeyboardButtonClick(keyboardButton)
			},
			onClick = {
				viewModel.onKeyboardButtonClick(keyboardButton)
			}
		) {
			KeyboardButtonText(keyboardButton.getText())
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
	keyboardButton: KeyboardButton,
	onClick: () -> Unit,
	onLongClick: (() -> Unit)? = null,
	content: @Composable BoxScope.() -> Unit
) {
	val interactionSource = remember { MutableInteractionSource() }

	val isPressed by interactionSource.collectIsPressedAsState()
	val cornerSize by animateDpAsState(
		targetValue = if (isPressed) {
			MaterialTheme.space.regular
		} else {
			MaterialTheme.space.small
		}
	)

	val color = keyboardButton.getColor()
	val shape = RoundedCornerShape(cornerSize)
	val tonalElevation = 1.dp

	Surface(
		modifier = modifier
			.clip(shape)
			.combinedClickable(
				role = Role.Button,
				interactionSource = interactionSource,
				indication = rememberRipple(),
				onLongClick = onLongClick,
				onClick = onClick
			),
		shape = shape,
		color = color,
		tonalElevation = tonalElevation
	) {
		Box(
			modifier = Modifier.defaultMinSize(
				minWidth = 48.dp,
				minHeight = 48.dp
			),
			contentAlignment = Alignment.Center,
			content = content
		)
	}
}

@Composable
fun ElementList(modifier: Modifier = Modifier) {
	val viewModel = viewModel<MainViewModel>()

	val elementList by viewModel.elementList

	val space = MaterialTheme.space.regular

	var search by rememberSaveable(viewModel.search) { mutableStateOf(String()) }

	Surface(modifier = modifier) {
		LazyColumn(
			contentPadding = PaddingValues(space),
			verticalArrangement = Arrangement.spacedBy(space)
		) {
			elementListHeader(
				search = search,
				onSearchChange = { search = it }
			)

			keyboardContent(viewModel, elementList, search)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
private fun LazyListScope.elementListHeader(
	search: String,
	onSearchChange: (String) -> Unit
) {
	item {
		val viewModel = viewModel<MainViewModel>()
		val elementList by viewModel.elementList
		val focusManager = LocalFocusManager.current

		val space = MaterialTheme.space.regular

		var name by rememberSaveable(viewModel.addition) { mutableStateOf(String()) }
		var value by rememberSaveable(viewModel.addition) { mutableStateOf(String()) }

		val isAdditionButtonEnabled = name.isNotBlank()

		val scope = rememberCoroutineScope()

		val context = LocalContext.current

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
								if (viewModel.addition && noneName) {
									scope.launch {
										val element = Element(name.trim(), value)
										viewModel.addElementToList(context, element)
									}
								}

								viewModel.addition = !viewModel.addition
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
							onValueChange = onSearchChange,
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
}

fun LazyListScope.keyboardContent(
	viewModel: MainViewModel,
	elementList: List<Element>,
	search: String
) {
	val filterEnabled = viewModel.search && search.isNotBlank()
	val elementFilteredList = elementList.filterIf(filterEnabled) {
		it.name.contains(search, ignoreCase = true)
	}

	items(elementFilteredList) { element ->
		val context = LocalContext.current

		ElementListItem(
			modifier = Modifier.fillMaxWidth(),
			element = element,
			onClick = {
				viewModel.onElementItemClick(element)
			},
			onLongClick = {
				viewModel.onLongElementItemClick(context, element)
			}
		)
	}

	if (elementList.isEmpty()) {
		item {
			ElementEmptyListCard()
		}
	}

	if (elementList.isNotEmpty() && elementFilteredList.isEmpty()) {
		item {
			ElementEmptySearchListCard()
		}
	}
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ElementListItem(
	modifier: Modifier = Modifier,
	element: Element,
	onClick: () -> Unit,
	onLongClick: () -> Unit,
) {
	val (name, value) = element

	val space = MaterialTheme.space.regular
	val textStyle = MaterialTheme.typography.titleSmall
	val shape = MaterialTheme.shapes.medium
	val tonalElevation = 3.dp

	Surface(
		modifier = modifier
			.clip(shape)
			.combinedClickable(
				role = Role.Button,
				onClick = onClick,
				onLongClick = onLongClick
			),
		shape = shape,
		tonalElevation = tonalElevation
	) {
		Row(
			modifier = Modifier.padding(space),
			horizontalArrangement = Arrangement.spacedBy(space),
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

@Composable
private fun ElementEmptySearchListCard() {
	val space = MaterialTheme.space.regular

	Surface(
		shape = MaterialTheme.shapes.small,
		color = MaterialTheme.colorScheme.primaryContainer
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(space),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			Icon(imageVector = Icons.Default.Search, contentDescription = null)

			Text(
				text = stringResource(id = R.string.empty_element_search_list),
				style = MaterialTheme.typography.titleSmall,
				textAlign = TextAlign.Center
			)
		}
	}
}

@Composable
private fun ElementEmptyListCard() {
	val space = MaterialTheme.space.large

	Surface(
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.primaryContainer
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(space),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Text(
				text = stringResource(id = R.string.empty_element_list),
				style = MaterialTheme.typography.titleSmall,
				textAlign = TextAlign.Center
			)

			Text(
				text = stringResource(id = R.string.empty_element_list_content),
				style = MaterialTheme.typography.bodyLarge,
				textAlign = TextAlign.Center
			)
		}
	}
}
