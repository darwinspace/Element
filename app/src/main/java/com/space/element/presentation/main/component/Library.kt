package com.space.element.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Functions
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.space.element.R
import com.space.element.domain.model.Element
import com.space.element.domain.model.ElementListItem
import com.space.element.domain.model.Function
import com.space.element.presentation.component.ElementTextField
import com.space.element.presentation.main.model.LibraryState
import com.space.element.presentation.main.model.LibraryState.Create
import com.space.element.presentation.main.model.LibraryState.Edit
import com.space.element.presentation.main.model.LibraryState.Normal
import com.space.element.presentation.main.model.LibraryState.Search
import com.space.element.presentation.theme.ElementTheme
import com.space.element.presentation.main.model.LibraryState.Function as FunctionState

@Preview
@Composable
fun LibraryPreview() {
	val elementList = remember {
		List(10) {
			Element(name = "Item $it", value = it.toString())
		}
	}
	var libraryState by remember { mutableStateOf<LibraryState>(Normal) }
	var elementListQuery by remember { mutableStateOf(String()) }
	var elementName by remember { mutableStateOf(String()) }
	var elementValue by remember { mutableStateOf(String()) }

	ElementTheme {
		Library(
			libraryState = { libraryState },
			onLibraryStateChange = { libraryState = it },
			elementList = { elementList },
			onElementListItemClick = { throw NotImplementedError() },
			elementListQuery = { elementListQuery },
			onElementListQueryChange = { elementListQuery = it },
			elementName = { elementName },
			onElementNameChange = { elementName = it },
			elementValue = { elementValue },
			onElementValueChange = { elementValue = it },
			isCreateElementButtonEnabled = { true },
			onRemoveClick = { },
			onCreateElementClick = { },
			functionList = { emptyList() },
			onFunctionListItemClick = { }
		)
	}
}

@Composable
fun Library(
	libraryState: () -> LibraryState,
	onLibraryStateChange: (LibraryState) -> Unit,
	modifier: Modifier = Modifier,
	elementList: () -> List<Element>,
	onElementListItemClick: (Element) -> Unit,
	elementListQuery: () -> String,
	onElementListQueryChange: (String) -> Unit,
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	isCreateElementButtonEnabled: () -> Boolean,
	onRemoveClick: (List<ElementListItem>) -> Unit,
	onCreateElementClick: () -> Unit,
	functionList: () -> List<Function>,
	onFunctionListItemClick: (Function) -> Unit
) {
	val mode = libraryState()
	val elementData = elementList()
	// TODO: Use remember with listSaver.
	val elementDataList = remember(elementData, mode) {
		elementData.map { ElementListItem(element = it, selected = false) }.toMutableStateList()
	}
	val functionDataList = functionList()

	Surface(modifier = modifier) {
		Column {
			LibraryHeader(
				elementList = elementDataList,
				mode = mode,
				onLibraryStateChange = onLibraryStateChange,
				isCreateElementButtonEnabled = isCreateElementButtonEnabled,
				onRemoveClick = onRemoveClick,
				onCreateElementClick = onCreateElementClick
			)

			AnimatedVisibility(
				visible = mode is Create
			) {
				LibraryCreateElementForm(
					elementName = elementName,
					onElementNameChange = onElementNameChange,
					elementValue = elementValue,
					onElementValueChange = onElementValueChange,
					onDone = {
						val enabled = isCreateElementButtonEnabled()
						if (enabled) {
							onCreateElementClick()
						}
					}
				)
			}

			AnimatedVisibility(
				visible = mode is Search
			) {
				ElementListSearchTextField(
					value = elementListQuery,
					onValueChange = onElementListQueryChange
				)
			}

			AnimatedVisibility(
				visible = mode !is FunctionState && elementDataList.isEmpty()
			) {
				ElementListEmptyCard()
			}

			AnimatedVisibility(
				visible = mode !is FunctionState && elementDataList.isNotEmpty()
			) {
				ElementList(
					mode = mode,
					list = elementDataList,
					onClick = onElementListItemClick
				)
			}

			AnimatedVisibility(visible = mode is FunctionState) {
				FunctionList(
					list = functionDataList,
					onClick = onFunctionListItemClick
				)
			}
		}
	}
}

@Composable
fun LibraryHeader(
	elementList: List<ElementListItem>,
	mode: LibraryState,
	onLibraryStateChange: (LibraryState) -> Unit,
	isCreateElementButtonEnabled: () -> Boolean,
	onRemoveClick: (List<ElementListItem>) -> Unit,
	onCreateElementClick: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 24.dp, end = 24.dp, top = 24.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		AnimatedVisibility(
			visible = mode is Create || mode is Edit || mode is FunctionState
		) {
			Row {
				FilledTonalIconButton(
					modifier = Modifier.size(48.dp),
					onClick = {
						onLibraryStateChange(Normal)
					}
				) {
					Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
				}

				Spacer(modifier = Modifier.width(16.dp))
			}
		}

		AnimatedVisibility(
			modifier = Modifier.weight(1f),
			visible = mode is Normal || mode is Create || mode is Search || mode is FunctionState
		) {
			AnimatedVisibility(
				visible = mode is Normal || mode is Create || mode is Search,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				Button(
					modifier = Modifier
						.fillMaxWidth()
						.heightIn(48.dp),
					enabled = isCreateElementButtonEnabled(),
					shape = MaterialTheme.shapes.small,
					onClick = onCreateElementClick
				) {
					Text(text = stringResource(R.string.button_add_element))
				}
			}

			AnimatedVisibility(
				visible = mode is FunctionState,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				Button(
					modifier = Modifier
						.fillMaxWidth()
						.heightIn(48.dp),
					enabled = true,
					shape = MaterialTheme.shapes.small,
					colors = ButtonDefaults.buttonColors(
						containerColor = MaterialTheme.colorScheme.tertiary,
						contentColor = MaterialTheme.colorScheme.onTertiary
					),
					onClick = {}
				) {
					Text(text = "Create function")
				}
			}
		}

		AnimatedVisibility(visible = mode is Normal) {
			Row {
				Spacer(modifier = Modifier.width(16.dp))

				FilledTonalIconButton(
					modifier = Modifier.size(48.dp),
					onClick = {
						onLibraryStateChange(FunctionState)
					}
				) {
					Icon(imageVector = Icons.Outlined.Functions, contentDescription = null)
				}
			}
		}

		AnimatedVisibility(
			visible = mode is Normal && elementList.isNotEmpty() || mode is Edit
		) {
			Row {
				Spacer(modifier = Modifier.width(16.dp))

				Box {
					androidx.compose.animation.AnimatedVisibility(
						visible = mode is Normal,
						enter = fadeIn(),
						exit = fadeOut()
					) {
						FilledTonalIconButton(
							modifier = Modifier.size(48.dp),
							onClick = {
								onLibraryStateChange(Edit)
							}
						) {
							Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
						}
					}

					androidx.compose.animation.AnimatedVisibility(
						visible = mode is Edit,
						enter = fadeIn(),
						exit = fadeOut()
					) {
						val enabled = elementList.any { it.selected }
						FilledIconButton(
							modifier = Modifier.size(48.dp),
							enabled = enabled,
							onClick = {
								onRemoveClick(elementList)
								onLibraryStateChange(Normal)
							}
						) {
							androidx.compose.animation.AnimatedVisibility(
								visible = enabled,
								enter = fadeIn(),
								exit = fadeOut()
							) {
								Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
							}

							androidx.compose.animation.AnimatedVisibility(
								visible = !enabled,
								enter = fadeIn(),
								exit = fadeOut()
							) {
								Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
							}
						}
					}
				}
			}
		}

		AnimatedVisibility(
			visible = mode is Normal && elementList.isNotEmpty() || mode is Search
		) {
			Row {
				Spacer(modifier = Modifier.width(16.dp))

				FilledTonalIconButton(
					modifier = Modifier.size(48.dp),
					onClick = {
						if (mode is Normal) {
							onLibraryStateChange(Search)
						} else if (mode is Search) {
							onLibraryStateChange(Normal)
						}
					}
				) {
					AnimatedVisibility(
						visible = mode is Normal,
						enter = fadeIn(),
						exit = fadeOut()
					) {
						Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
					}

					AnimatedVisibility(
						visible = mode is Search,
						enter = fadeIn(),
						exit = fadeOut()
					) {
						Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
					}
				}
			}
		}
	}
}

@Composable
fun LibraryCreateElementForm(
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	onDone: () -> Unit
) {
	Column(
		modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		ElementNameTextField(
			elementName = elementName,
			onElementNameChange = onElementNameChange
		)

		ElementValueTextField(
			elementValue = elementValue,
			onElementValueChange = onElementValueChange,
			onDone = onDone
		)
	}
}

@Composable
private fun ElementNameTextField(
	elementName: () -> String,
	onElementNameChange: (String) -> Unit
) {
	ElementTextField(
		modifier = Modifier.fillMaxWidth(),
		value = elementName(),
		onValueChange = onElementNameChange,
		placeholder = {
			Text(
				text = stringResource(R.string.element_name),
				style = MaterialTheme.typography.bodyMedium
			)
		},
		keyboardOptions = KeyboardOptions(
			capitalization = KeyboardCapitalization.Sentences,
			imeAction = ImeAction.Next
		)
	)
}

@Composable
private fun ElementValueTextField(
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	onDone: () -> Unit
) {
	ElementTextField(
		modifier = Modifier.fillMaxWidth(),
		value = elementValue(),
		onValueChange = onElementValueChange,
		placeholder = {
			Text(
				text = stringResource(R.string.element_value),
				style = MaterialTheme.typography.bodyMedium
			)
		},
		keyboardActions = KeyboardActions {
			onDone()
		},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Decimal,
			imeAction = ImeAction.Done
		)
	)
}

@Composable
fun ElementListSearchTextField(
	value: () -> String,
	onValueChange: (String) -> Unit
) {
	Box(modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)) {
		ElementTextField(
			modifier = Modifier.fillMaxWidth(),
			value = value(),
			onValueChange = onValueChange,
			placeholder = {
				Text(
					text = stringResource(R.string.search),
					style = MaterialTheme.typography.bodyMedium
				)
			},
			trailingIcon = {
				Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
			},
			keyboardOptions = KeyboardOptions(
				imeAction = ImeAction.Search
			)
		)
	}
}

@Composable
fun ElementList(
	mode: LibraryState,
	list: SnapshotStateList<ElementListItem>,
	onClick: (Element) -> Unit
) {
	LazyColumn(
		contentPadding = PaddingValues(24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		itemsIndexed(list, { _, item -> item.element.name }) { index, item ->
			ElementListItem(
				modifier = Modifier.fillMaxWidth(),
				elementListItem = item
			) {
				if (mode is Edit) {
					list[index] = item.copy(selected = !item.selected)
				} else {
					onClick(item.element)
				}
			}
		}
	}
}

@Composable
fun ElementListItem(
	modifier: Modifier = Modifier,
	elementListItem: ElementListItem,
	onClick: () -> Unit
) {
	val color by animateColorAsState(
		targetValue = if (elementListItem.selected) {
			MaterialTheme.colorScheme.secondary
		} else {
			MaterialTheme.colorScheme.secondaryContainer
		},
		label = "ElementListItemSurfaceColor"
	)
	Surface(
		modifier = modifier,
		shape = MaterialTheme.shapes.medium,
		color = color,
		onClick = onClick
	) {
		Row(
			modifier = Modifier.padding(20.dp),
			horizontalArrangement = Arrangement.spacedBy(24.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				modifier = Modifier.weight(1f),
				text = elementListItem.element.name,
				textAlign = TextAlign.Justify,
				style = MaterialTheme.typography.titleSmall
			)

			Text(
				text = elementListItem.element.value,
				style = MaterialTheme.typography.bodyMedium
			)
		}
	}
}

@Composable
fun ElementListEmptyCard() {
	Surface(
		modifier = Modifier.padding(24.dp),
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.primaryContainer
	) {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(32.dp),
			text = buildAnnotatedString {
				append(text = stringResource(R.string.empty_element_list_content_action))
				withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
					append(text = " \"")
					append(text = stringResource(R.string.button_add_element))
					append(text = "\" ")
				}
				append(text = stringResource(R.string.empty_element_list_content_reason))
			},
			style = MaterialTheme.typography.bodyMedium,
			textAlign = TextAlign.Center
		)
	}
}

@Composable
fun FunctionList(list: List<Function>, onClick: (Function) -> Unit) {
	LazyColumn(
		contentPadding = PaddingValues(24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		items(list) { function ->
			FunctionListItem(
				function = function,
				onClick = { onClick(function) }
			)
		}
	}
}

@Composable
fun FunctionListItem(
	function: Function,
	onClick: () -> Unit
) {
	Surface(
		shape = MaterialTheme.shapes.medium,
		onClick = onClick
	) {
		Column {
			Surface(
				modifier = Modifier.fillMaxWidth(),
				color = MaterialTheme.colorScheme.tertiary
			) {
				val text = remember {
					buildAnnotatedString {
						withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
							append(function.name)
						}

						withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
							append("(x)")
						}
					}
				}

				Text(
					modifier = Modifier
						.padding(16.dp)
						.fillMaxWidth(),
					text = text,
					style = MaterialTheme.typography.titleSmall
				)
			}

			Surface(
				modifier = Modifier.fillMaxWidth(),
				color = MaterialTheme.colorScheme.tertiaryContainer
			) {
				Text(
					modifier = Modifier
						.padding(16.dp)
						.fillMaxWidth(),
					text = function.definition,
					style = MaterialTheme.typography.bodyMedium
				)
			}
		}
	}
}
