package com.space.element.presentation.main.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Functions
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.space.element.R
import com.space.element.domain.model.Element
import com.space.element.domain.model.ElementListItem
import com.space.element.domain.model.Function
import com.space.element.domain.model.FunctionListItem
import com.space.element.presentation.component.ElementTextField
import com.space.element.presentation.main.model.LibraryState
import com.space.element.presentation.theme.ElementTheme
import com.space.element.util.rememberElementList
import com.space.element.util.rememberEmptyListText
import com.space.element.util.rememberFunctionList
import com.space.element.util.rememberFunctionName

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun LibraryPreview() {
	val elementList = remember {
		List(10) {
			Element(name = "Item $it", value = it.toString())
		}
	}
	var libraryState by remember { mutableStateOf<LibraryState>(LibraryState.ElementState.List) }
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
			elementListCreateButtonEnabled = { true },
			onCreateElementClick = { },
			onRemoveElementClick = { },
			functionList = { emptyList() },
			onFunctionListItemClick = { },
			functionName = { elementName },
			onFunctionNameChange = { elementName = it },
			functionDefinition = { elementValue },
			onFunctionDefinitionChange = { elementValue = it },
			functionListCreateButtonEnabled = { true },
			onCreateFunctionClick = { },
			onRemoveFunctionClick = { }
		)
	}
}

@Composable
fun Library(
	modifier: Modifier = Modifier,
	libraryState: () -> LibraryState,
	onLibraryStateChange: (LibraryState) -> Unit,
	elementList: () -> List<Element>,
	onElementListItemClick: (Element) -> Unit,
	elementListQuery: () -> String,
	onElementListQueryChange: (String) -> Unit,
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	elementListCreateButtonEnabled: () -> Boolean,
	onCreateElementClick: () -> Unit,
	onRemoveElementClick: (List<ElementListItem>) -> Unit,
	functionList: () -> List<Function>,
	onFunctionListItemClick: (Function) -> Unit,
	functionName: () -> String,
	onFunctionNameChange: (String) -> Unit,
	functionDefinition: () -> String,
	onFunctionDefinitionChange: (String) -> Unit,
	functionListCreateButtonEnabled: () -> Boolean,
	onCreateFunctionClick: () -> Unit,
	onRemoveFunctionClick: (List<FunctionListItem>) -> Unit
) {
	val state = libraryState()
	val elementData = elementList()
	val elementDataList = rememberElementList(elementData, state)
	val functionData = functionList()
	val functionDataList = rememberFunctionList(functionData, state)

	Surface(modifier = modifier) {
		Column {
			LibraryHeader(
				libraryState = state,
				onLibraryStateChange = onLibraryStateChange,
				elementList = elementDataList,
				elementListCreateButtonEnabled = elementListCreateButtonEnabled,
				onCreateElementClick = onCreateElementClick,
				onRemoveElementClick = onRemoveElementClick,
				functionListCreateButtonEnabled = functionListCreateButtonEnabled,
				functionList = functionDataList,
				onCreateFunctionClick = onCreateFunctionClick,
				onRemoveFunctionClick = onRemoveFunctionClick
			)

			AnimatedVisibility(
				visible = state is LibraryState.ElementState.Create
			) {
				LibraryCreateElementForm(
					elementName = elementName,
					onElementNameChange = onElementNameChange,
					elementValue = elementValue,
					onElementValueChange = onElementValueChange,
					onDone = {
						val enabled = elementListCreateButtonEnabled()
						if (enabled) {
							onCreateElementClick()
						}
					}
				)
			}

			AnimatedVisibility(visible = state is LibraryState.FunctionState.Create) {
				LibraryCreateFunctionForm(
					functionName = functionName,
					onFunctionNameChange = onFunctionNameChange,
					functionDefinition = functionDefinition,
					onFunctionDefinitionChange = onFunctionDefinitionChange
				)
			}

			AnimatedVisibility(
				visible = state is LibraryState.ElementState.Search
			) {
				ElementListSearchTextField(
					value = elementListQuery,
					onValueChange = onElementListQueryChange
				)
			}

			AnimatedVisibility(
				visible = state is LibraryState.ElementState && elementDataList.isEmpty()
			) {
				ElementListEmptyCard()
			}

			AnimatedVisibility(
				visible = state is LibraryState.ElementState && elementDataList.isNotEmpty()
			) {
				ElementList(
					libraryState = state,
					list = elementDataList,
					onClick = onElementListItemClick
				)
			}

			AnimatedVisibility(visible = state is LibraryState.FunctionState && functionData.isEmpty()) {
				FunctionListEmptyCard()
			}

			AnimatedVisibility(visible = state is LibraryState.FunctionState && functionData.isNotEmpty()) {
				FunctionList(
					libraryState = state,
					list = functionDataList,
					onClick = onFunctionListItemClick
				)
			}
		}
	}
}

@Composable
fun LibraryDragHandle(
	modifier: Modifier = Modifier,
	width: Dp,
	height: Dp
) {
	Surface(
		modifier = modifier,
		color = MaterialTheme.colorScheme.onSurface,
		shape = MaterialTheme.shapes.extraLarge
	) {
		Box(
			modifier = Modifier.size(
				width = width,
				height = height
			)
		)
	}
}

@Composable
fun LibraryCreateFunctionForm(
	functionName: () -> String,
	onFunctionNameChange: (String) -> Unit,
	functionDefinition: () -> String,
	onFunctionDefinitionChange: (String) -> Unit
) {
	Column(
		modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		FunctionNameTextField(
			functionName = functionName,
			onFunctionNameChange = onFunctionNameChange
		)

		FunctionDefinitionTextField(
			functionDefinition = functionDefinition,
			onFunctionDefinitionChange = onFunctionDefinitionChange
		)
	}
}

@Composable
fun FunctionNameTextField(functionName: () -> String, onFunctionNameChange: (String) -> Unit) {
	ElementTextField(
		modifier = Modifier.fillMaxWidth(),
		value = functionName(),
		onValueChange = onFunctionNameChange,
		placeholder = {
			Text(
				text = "Name",
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
fun FunctionDefinitionTextField(
	functionDefinition: () -> String,
	onFunctionDefinitionChange: (String) -> Unit
) {
	ElementTextField(
		modifier = Modifier.fillMaxWidth(),
		value = functionDefinition(),
		onValueChange = onFunctionDefinitionChange,
		singleLine = false,
		placeholder = {
			Text(
				text = "Definition",
				style = MaterialTheme.typography.bodyMedium
			)
		},
		keyboardActions = KeyboardActions {},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Text,
			imeAction = ImeAction.Done
		)
	)
}

@Composable
fun LibraryHeader(
	libraryState: LibraryState,
	onLibraryStateChange: (LibraryState) -> Unit,
	elementList: List<ElementListItem>,
	elementListCreateButtonEnabled: () -> Boolean,
	onCreateElementClick: () -> Unit,
	onRemoveElementClick: (List<ElementListItem>) -> Unit,
	functionList: List<FunctionListItem>,
	functionListCreateButtonEnabled: () -> Boolean,
	onCreateFunctionClick: () -> Unit,
	onRemoveFunctionClick: (List<FunctionListItem>) -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 24.dp, end = 24.dp, top = 24.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		AnimatedVisibility(
			visible = libraryState is LibraryState.ElementState.Create
					|| libraryState is LibraryState.ElementState.Edit
					|| libraryState is LibraryState.FunctionState
		) {
			CloseButton(
				modifier = Modifier.padding(end = 16.dp),
				onClick = {
					if (libraryState is LibraryState.FunctionState.Create
						|| libraryState is LibraryState.FunctionState.Edit
					) {
						onLibraryStateChange(LibraryState.FunctionState.List)
					} else {
						onLibraryStateChange(LibraryState.ElementState.List)
					}
				}
			)
		}

		AnimatedVisibility(
			modifier = Modifier.weight(1f),
			visible = libraryState is LibraryState.ElementState.List
					|| libraryState is LibraryState.ElementState.Create || libraryState is LibraryState.ElementState.Search
					|| libraryState is LibraryState.FunctionState.List || libraryState is LibraryState.FunctionState.Create
		) {
			AnimatedVisibility(
				visible = libraryState is LibraryState.ElementState.List || libraryState is LibraryState.ElementState.Create || libraryState is LibraryState.ElementState.Search,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				CreateElementButton(
					enabled = elementListCreateButtonEnabled,
					onClick = onCreateElementClick
				)
			}

			AnimatedVisibility(
				visible = libraryState is LibraryState.FunctionState.List || libraryState is LibraryState.FunctionState.Create,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				CreateFunctionButton(
					enabled = functionListCreateButtonEnabled,
					onClick = onCreateFunctionClick
				)
			}
		}

		AnimatedVisibility(visible = libraryState is LibraryState.ElementState.List) {
			FunctionButton(
				modifier = Modifier.padding(start = 16.dp),
				onClick = {
					onLibraryStateChange(LibraryState.FunctionState.List)
				}
			)
		}

		AnimatedVisibility(
			visible = (libraryState is LibraryState.ElementState.List && elementList.isNotEmpty() || libraryState is LibraryState.ElementState.Edit)
					|| (libraryState is LibraryState.FunctionState.List && functionList.isNotEmpty() || libraryState is LibraryState.FunctionState.Edit)
		) {
			RemoveButton(
				modifier = Modifier.padding(start = 16.dp),
				libraryState = libraryState,
				onLibraryStateChange = onLibraryStateChange,
				elementRemoveEnabled = elementList.any { it.selected },
				onRemoveElementClick = { onRemoveElementClick(elementList) },
				functionRemoveEnabled = functionList.any { it.selected },
				onRemoveFunctionClick = { onRemoveFunctionClick(functionList) }
			)
		}

		AnimatedVisibility(
			visible = libraryState is LibraryState.ElementState.List && elementList.isNotEmpty() || libraryState is LibraryState.ElementState.Search
		) {
			SearchButton(
				modifier = Modifier.padding(start = 16.dp),
				libraryState = libraryState,
				onClick = {
					if (libraryState is LibraryState.ElementState.List) {
						onLibraryStateChange(LibraryState.ElementState.Search)
					} else if (libraryState is LibraryState.ElementState.Search) {
						onLibraryStateChange(LibraryState.ElementState.List)
					}
				}
			)
		}
	}
}

@Composable
private fun CloseButton(
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	OutlinedIconButton(
		modifier = modifier.size(48.dp),
		border = BorderStroke(
			width = 2.dp,
			color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
		),
		onClick = onClick
	) {
		Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
	}
}

@Composable
private fun CreateFunctionButton(
	enabled: () -> Boolean,
	onClick: () -> Unit
) {
	Button(
		modifier = Modifier
			.fillMaxWidth()
			.heightIn(48.dp),
		enabled = enabled(),
		shape = MaterialTheme.shapes.small,
		colors = ButtonDefaults.buttonColors(
			containerColor = MaterialTheme.colorScheme.tertiary,
			contentColor = MaterialTheme.colorScheme.onTertiary
		),
		onClick = onClick
	) {
		Text(text = "Create function")
	}
}

@Composable
private fun CreateElementButton(
	enabled: () -> Boolean,
	onClick: () -> Unit
) {
	Button(
		modifier = Modifier
			.fillMaxWidth()
			.heightIn(48.dp),
		enabled = enabled(),
		shape = MaterialTheme.shapes.small,
		border = BorderStroke(
			width = 2.dp,
			color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
		),
		onClick = onClick
	) {
		Text(text = stringResource(R.string.button_add_element))
	}
}

@Composable
private fun RemoveButton(
	modifier: Modifier = Modifier,
	libraryState: LibraryState,
	onLibraryStateChange: (LibraryState) -> Unit,
	elementRemoveEnabled: Boolean,
	onRemoveElementClick: () -> Unit,
	functionRemoveEnabled: Boolean,
	onRemoveFunctionClick: () -> Unit
) {
	Box(modifier = modifier) {
		AnimatedVisibility(
			visible = libraryState is LibraryState.ElementState.List || libraryState is LibraryState.FunctionState.List,
			enter = fadeIn(),
			exit = fadeOut()
		) {
			OutlinedIconButton(
				modifier = Modifier.size(48.dp),
				border = BorderStroke(
					width = 2.dp,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
				),
				onClick = {
					if (libraryState is LibraryState.ElementState.List) {
						onLibraryStateChange(LibraryState.ElementState.Edit)
					}

					if (libraryState is LibraryState.FunctionState.List) {
						onLibraryStateChange(LibraryState.FunctionState.Edit)
					}
				}
			) {
				Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
			}
		}

		AnimatedVisibility(
			visible = libraryState is LibraryState.ElementState.Edit || libraryState is LibraryState.FunctionState.Edit,
			enter = fadeIn(),
			exit = fadeOut(),
		) {
			OutlinedIconButton(
				modifier = modifier.size(48.dp),
				enabled = elementRemoveEnabled || functionRemoveEnabled,
				colors = IconButtonDefaults.filledIconButtonColors(
					containerColor = MaterialTheme.colorScheme.errorContainer
				),
				border = BorderStroke(
					width = 2.dp,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
				),
				onClick = {
					if (libraryState is LibraryState.ElementState.Edit) {
						onRemoveElementClick()
						onLibraryStateChange(LibraryState.ElementState.List)
					}

					if (libraryState is LibraryState.FunctionState.Edit) {
						onRemoveFunctionClick()
						onLibraryStateChange(LibraryState.FunctionState.List)
					}
				}
			) {
				AnimatedVisibility(
					visible = elementRemoveEnabled || functionRemoveEnabled,
					enter = fadeIn(),
					exit = fadeOut()
				) {
					Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
				}

				AnimatedVisibility(
					visible = !elementRemoveEnabled || !functionRemoveEnabled,
					enter = fadeIn(),
					exit = fadeOut()
				) {
					Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
				}
			}
		}
	}
}

@Composable
private fun RowScope.SearchButton(
	modifier: Modifier,
	libraryState: LibraryState,
	onClick: () -> Unit,
) {
	OutlinedIconButton(
		modifier = modifier.size(48.dp),
		border = BorderStroke(
			width = 2.dp,
			color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
		),
		onClick = onClick
	) {
		AnimatedVisibility(
			visible = libraryState is LibraryState.ElementState.List,
			enter = fadeIn(),
			exit = fadeOut()
		) {
			Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
		}

		AnimatedVisibility(
			visible = libraryState is LibraryState.ElementState.Search,
			enter = fadeIn(),
			exit = fadeOut()
		) {
			Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
		}
	}
}

@Composable
private fun FunctionButton(modifier: Modifier, onClick: () -> Unit) {
	OutlinedIconButton(
		modifier = modifier.size(48.dp),
		border = BorderStroke(
			width = 2.dp,
			color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
		),
		onClick = onClick
	) {
		Icon(imageVector = Icons.Outlined.Functions, contentDescription = null)
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
	ElementTextField(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 24.dp, top = 24.dp, end = 24.dp),
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

@Composable
fun ElementList(
	libraryState: LibraryState,
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
				if (libraryState is LibraryState.ElementState.Edit) {
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
	val borderWidth by animateDpAsState(
		targetValue = if (elementListItem.selected) 4.dp else 2.dp,
		label = "ElementListItemSurfaceBorderWidth"
	)
	val borderColor by animateColorAsState(
		targetValue = if (elementListItem.selected) {
			MaterialTheme.colorScheme.primary
		} else {
			MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
		},
		label = "ElementListItemSurfaceBorderColor"
	)
	Surface(
		modifier = modifier,
		shape = MaterialTheme.shapes.medium,
		border = BorderStroke(
			width = borderWidth,
			color = borderColor
		),
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
		color = MaterialTheme.colorScheme.errorContainer
	) {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(32.dp),
			text = rememberEmptyListText(
				stringResource(R.string.empty_element_list_content_action),
				stringResource(R.string.button_add_element),
				stringResource(R.string.empty_element_list_content_reason)
			),
			style = MaterialTheme.typography.bodyMedium,
			textAlign = TextAlign.Center
		)
	}
}

@Composable
fun FunctionList(
	libraryState: LibraryState,
	list: SnapshotStateList<FunctionListItem>,
	onClick: (Function) -> Unit
) {
	LazyColumn(
		contentPadding = PaddingValues(24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		itemsIndexed(list) { index, item ->
			FunctionListItem(
				functionListItem = item,
				onClick = {
					if (libraryState is LibraryState.FunctionState.Edit) {
						list[index] = item.copy(selected = !item.selected)
					} else {
						onClick(item.function)
					}
				}
			)
		}
	}
}

@Composable
fun FunctionListItem(
	functionListItem: FunctionListItem,
	onClick: () -> Unit
) {
	val borderWidth by animateDpAsState(
		targetValue = if (functionListItem.selected) 4.dp else 0.dp,
		label = "FunctionListItemSurfaceBorderWidth"
	)
	val borderColor by animateColorAsState(
		targetValue = if (functionListItem.selected) {
			MaterialTheme.colorScheme.tertiary
		} else {
			Color.Transparent
		},
		label = "FunctionListItemSurfaceBorderColor"
	)

	Surface(
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.tertiaryContainer,
		border = BorderStroke(
			width = borderWidth,
			color = borderColor
		),
		onClick = onClick
	) {
		Column {
			Surface(
				modifier = Modifier
					.padding(8.dp)
					.fillMaxWidth(),
				shape = MaterialTheme.shapes.extraSmall,
				color = MaterialTheme.colorScheme.tertiary
			) {
				Text(
					modifier = Modifier
						.padding(8.dp)
						.fillMaxWidth(),
					text = rememberFunctionName(functionListItem.function),
					style = MaterialTheme.typography.titleSmall
				)
			}

			Text(
				modifier = Modifier
					.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
					.fillMaxWidth(),
				text = functionListItem.function.definition,
				style = MaterialTheme.typography.bodyMedium
			)
		}
	}
}

@Composable
fun FunctionListEmptyCard() {
	Surface(
		modifier = Modifier.padding(24.dp),
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.errorContainer
	) {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(32.dp),
			text = rememberEmptyListText(
				"Tap",
				"Create function",
				"to add a function."
			),
			style = MaterialTheme.typography.bodyMedium,
			textAlign = TextAlign.Center
		)
	}
}
