package com.space.element.presentation.main.component

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.space.element.R
import com.space.element.domain.model.Element
import com.space.element.presentation.main.*
import com.space.element.presentation.main.model.ElementListState
import com.space.element.util.filterIf


@Composable
fun ElementList(modifier: Modifier = Modifier) {
	val viewModel = viewModel<MainViewModel>()
	val elementList by viewModel.elementList.collectAsState()
	val state = viewModel.elementListState


	var search by rememberSaveable(state is ElementListState.SearchState) {
		mutableStateOf(String())
	}

	val elementFilteredList = remember(elementList) {
		elementList.filterIf(search.isNotBlank()) { (name) ->
			name.contains(search, ignoreCase = true)
		}
	}

	val padding = 24.dp
	val listContentPadding =
		PaddingValues(start = padding, top = 0.dp, end = padding, bottom = padding)

	LazyColumn(
		modifier = modifier,
		contentPadding = listContentPadding,
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		item {
			ElementListHeader(
				state = state,
				onStateChange = { viewModel.elementListState = it },
				search = search,
				onSearchChange = { search = it },
				elementName = "",
				onElementNameChange = { },
				elementValue = "",
				onElementValueChange = { },
				onAddElementClick = { }
			)
		}

		if (elementList.isEmpty()) {
			item {
				ElementEmptyListCard()
			}
		}

		if (elementFilteredList.isEmpty()) {
			item {
				ElementEmptySearchListCard()
			}
		}

		elementList(elementFilteredList) { element ->
			viewModel.onElementItemClick(element)
		}
	}
}


fun LazyListScope.elementList(
	elementList: List<Element>,
	onClick: (Element) -> Unit
) {
	items(elementList, { it.name }) { element ->
		ElementListItem(
			modifier = Modifier.fillMaxWidth(),
			element = element
		) {
			onClick(element)
		}
	}
}

@Composable
private fun ElementListItem(
	modifier: Modifier = Modifier,
	element: Element,
	onClick: () -> Unit
) {
	val (name, value) = element

	val space = 24.dp
	val textStyle = MaterialTheme.typography.titleSmall
	val shape = MaterialTheme.shapes.medium
	val tonalElevation = 3.dp

	Surface(
		modifier = modifier
			.clip(shape)
			.clickable(role = Role.Button, onClick = onClick),
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
	val space = 24.dp
	Surface(
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.primaryContainer
	) {
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.padding(space),
			text = stringResource(R.string.empty_element_search_list),
			fontStyle = FontStyle.Italic,
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center
		)
	}
}

@Composable
private fun ElementEmptyListCard() {
	val space = 32.dp
	val text = buildAnnotatedString {
		append(text = stringResource(R.string.empty_element_list_content_action))
		withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
			append(text = " \"")
			append(text = stringResource(R.string.button_add_element))
			append(text = "\" ")
		}
		append(text = stringResource(R.string.empty_element_list_content_reason))
	}

	Surface(
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.primaryContainer
	) {
		Text(
			text = text,
			modifier = Modifier
				.fillMaxWidth()
				.padding(space),
			fontStyle = FontStyle.Italic,
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ElementListHeader(
	state: ElementListState,
	onStateChange: (ElementListState) -> Unit,
	search: String,
	onSearchChange: (String) -> Unit,
	elementName: String,
	onElementNameChange: (String) -> Unit,
	elementValue: String,
	onElementValueChange: (String) -> Unit,
	onAddElementClick: () -> Unit,
) {
	val idleMode = state is ElementListState.IdleState
	val addMode = state is ElementListState.AddState
	val removeMode = state is ElementListState.RemoveState
	val searchMode = state is ElementListState.SearchState

	val height = 56.dp
	val size = 48.dp

	Row(
		modifier = Modifier
			.height(height)
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		AnimatedVisibility(visible = removeMode || addMode) {
			FilledTonalIconButton(
				modifier = Modifier.size(size),
				onClick = onAddElementClick
			) {
				Icon(imageVector = Icons.Default.Close, contentDescription = null)
			}
		}

		AnimatedVisibility(
			visible = addMode
		) {
			Spacer(modifier = Modifier.requiredWidth(24.dp))
		}

		AnimatedVisibility(
			visible = idleMode || addMode || searchMode || removeMode,
			modifier = Modifier.weight(1f)
		) {
			AnimatedVisibility(
				visible = idleMode || addMode,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				ElementListAddButton(
					modifier = Modifier
						.padding(vertical = (height - size) / 2)
						.height(size),
					onClick = onAddElementClick
				)
			}

			AnimatedVisibility(
				visible = searchMode,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				OutlinedTextField(
					value = search,
					onValueChange = onSearchChange,
					placeholder = {
						Text(text = stringResource(R.string.search))
					},
					shape = MaterialTheme.shapes.small
				)
			}
		}

		AnimatedVisibility(visible = idleMode) {
			Spacer(modifier = Modifier.requiredWidth(24.dp))
		}

		AnimatedVisibility(visible = idleMode || removeMode) {
			AnimatedVisibility(visible = idleMode) {
				FilledTonalIconButton(
					modifier = Modifier.size(size),
					onClick = {
						TODO()
					}
				) {
					Icon(imageVector = Icons.Default.Delete, contentDescription = null)
				}
			}

			AnimatedVisibility(visible = removeMode) {
				FilledIconButton(
					modifier = Modifier.size(size),
					onClick = {
						TODO()
					}
				) {
					Icon(imageVector = Icons.Default.Delete, contentDescription = null)
				}
			}
		}

		AnimatedVisibility(visible = idleMode || searchMode) {
			Spacer(modifier = Modifier.requiredWidth(24.dp))
		}

		AnimatedVisibility(visible = idleMode || searchMode) {
			FilledTonalIconButton(
				modifier = Modifier.size(size),
				onClick = {
					TODO()
				}
			) {
				AnimatedVisibility(
					visible = idleMode,
					enter = fadeIn(),
					exit = fadeOut()
				) {
					Icon(imageVector = Icons.Default.Search, contentDescription = null)
				}

				AnimatedVisibility(
					visible = searchMode,
					enter = fadeIn(),
					exit = fadeOut()
				) {
					Icon(imageVector = Icons.Default.Close, contentDescription = null)
				}
			}
		}
	}

	AnimatedVisibility(
		visible = addMode,
		enter = expandVertically(),
		exit = shrinkVertically(),
	) {
		Column {
			Spacer(modifier = Modifier.requiredHeight(24.dp))

			OutlinedTextField(
				modifier = Modifier.fillMaxWidth(),
				value = elementName,
				onValueChange = onElementNameChange,
				placeholder = {
					Text(text = stringResource(R.string.element_name))
				},
				shape = MaterialTheme.shapes.small
			)

			Spacer(modifier = Modifier.requiredHeight(24.dp))

			OutlinedTextField(
				modifier = Modifier.fillMaxWidth(),
				value = elementValue,
				onValueChange = onElementValueChange,
				placeholder = {
					Text(text = stringResource(R.string.element_value))
				},
				shape = MaterialTheme.shapes.small
			)
		}
	}
}

@Composable
private fun ElementListAddButton(
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	onClick: () -> Unit
) {
	Button(
		modifier = modifier,
		enabled = enabled,
		shape = MaterialTheme.shapes.small,
		onClick = onClick
	) {
		Text(text = stringResource(R.string.button_add_element))
	}
}