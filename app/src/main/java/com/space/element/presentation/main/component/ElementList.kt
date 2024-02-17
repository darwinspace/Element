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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
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
import com.space.element.presentation.component.ElementButton
import com.space.element.presentation.component.ElementIconButton
import com.space.element.presentation.component.ElementTextField
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ElementListMode.Create
import com.space.element.presentation.main.model.ElementListMode.Search
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ElementListPreview() {
	val elementList = remember {
		List(10) {
			Element("Item $it", it.toString())
		}
	}
	var elementListMode by remember { mutableStateOf<ElementListMode>(ElementListMode.Normal) }
	var elementListQuery by remember { mutableStateOf(String()) }
	var elementName by remember { mutableStateOf(String()) }
	var elementValue by remember { mutableStateOf(String()) }

	ElementTheme {
		ElementList(
			elementList = { elementList },
			elementListQuery = { elementListQuery },
			onElementListQueryChange = { elementListQuery = it },
			elementListMode = { elementListMode },
			onElementListModeChange = { elementListMode = it },
			onElementListItemClick = { throw NotImplementedError() },
			elementName = { elementName },
			onElementNameChange = { elementName = it },
			elementValue = { elementValue },
			onElementValueChange = { elementValue = it },
			isCreateElementButtonEnabled = { true },
			onCreateElementClick = { }
		)
	}
}

@Composable
fun ElementList(
	modifier: Modifier = Modifier,
	elementList: () -> List<Element>,
	elementListQuery: () -> String,
	onElementListQueryChange: (String) -> Unit,
	elementListMode: () -> ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	onElementListItemClick: (Element) -> Unit,
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit,
	isCreateElementButtonEnabled: () -> Boolean,
	onCreateElementClick: () -> Unit
) {
	val mode = elementListMode()
	val list = elementList()
	Surface(modifier = modifier) {
		Column {
			ElementListHeader(
				mode = mode,
				onModeChange = onElementListModeChange,
				isElementListEmpty = list.isEmpty(),
				isCreateElementButtonEnabled = isCreateElementButtonEnabled,
				onCreateElementClick = onCreateElementClick
			)

			AnimatedVisibility(
				visible = mode is Create
			) {
				ElementListHeaderElementForm(
					elementName = elementName,
					onElementNameChange = onElementNameChange,
					elementValue = elementValue,
					onElementValueChange = onElementValueChange
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
				visible = list.isEmpty()
			) {
				ElementListEmptyCard()
			}

			AnimatedVisibility(
				visible = list.isNotEmpty()
			) {
				ElementListContent(
					elementList = list,
					onClick = onElementListItemClick
				)
			}
		}
	}
}

@Composable
fun ElementListHeader(
	mode: ElementListMode,
	onModeChange: (ElementListMode) -> Unit,
	isElementListEmpty: Boolean,
	isCreateElementButtonEnabled: () -> Boolean,
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
			visible = mode is Create || mode is ElementListMode.Edit
		) {
			Row {
				ElementIconButton(
					onClick = {
						onModeChange(ElementListMode.Normal)
					}
				) {
					Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
				}

				Spacer(modifier = Modifier.width(16.dp))
			}
		}

		AnimatedVisibility(
			modifier = Modifier.weight(1f),
			visible = mode !is ElementListMode.Edit
		) {
			ElementButton(
				modifier = Modifier.fillMaxWidth(),
				text = stringResource(R.string.button_add_element),
				enabled = isCreateElementButtonEnabled(),
				onClick = onCreateElementClick
			)
		}

		AnimatedVisibility(
			visible = mode is ElementListMode.Normal && !isElementListEmpty || mode is ElementListMode.Edit
		) {
			Row {
				Spacer(modifier = Modifier.width(16.dp))

				ElementIconButton(
					onClick = {
						if (mode is ElementListMode.Normal) {
							onModeChange(ElementListMode.Edit)
						} else if (mode is ElementListMode.Edit) {
							onModeChange(ElementListMode.Normal)
						}
					}
				) {
					AnimatedVisibility(
						visible = mode is ElementListMode.Normal,
						enter = fadeIn(),
						exit = fadeOut()
					) {
						Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
					}

					AnimatedVisibility(
						visible = mode is ElementListMode.Edit,
						enter = fadeIn(),
						exit = fadeOut()
					) {
						Icon(imageVector = Icons.Outlined.Done, contentDescription = null)
					}
				}
			}
		}

		AnimatedVisibility(
			visible = mode is ElementListMode.Normal && !isElementListEmpty || mode is Search
		) {
			Row {
				Spacer(modifier = Modifier.width(16.dp))

				ElementIconButton(
					onClick = {
						if (mode is ElementListMode.Normal) {
							onModeChange(Search)
						} else if (mode is Search) {
							onModeChange(ElementListMode.Normal)
						}
					}
				) {
					AnimatedVisibility(
						visible = mode is ElementListMode.Normal,
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
fun ElementListHeaderElementForm(
	elementName: () -> String,
	onElementNameChange: (String) -> Unit,
	elementValue: () -> String,
	onElementValueChange: (String) -> Unit
) {
	Column(
		modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)
	) {
		ElementNameTextField(
			elementName = elementName,
			onElementNameChange = onElementNameChange
		)

		Spacer(modifier = Modifier.height(24.dp))

		ElementValueTextField(
			elementValue = elementValue,
			onElementValueChange = onElementValueChange
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
	onElementValueChange: (String) -> Unit
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
fun ElementListContent(
	elementList: List<Element>,
	onClick: (Element) -> Unit
) {
	LazyColumn(
		contentPadding = PaddingValues(24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		items(elementList, { it.name }) { element ->
			ElementListItem(
				modifier = Modifier.fillMaxWidth(),
				element = element,
				onClick = {
					onClick(element)
				}
			)
		}
	}
}

@Composable
fun ElementListItem(
	modifier: Modifier = Modifier,
	element: Element,
	onClick: () -> Unit
) {
	val selected by remember { mutableStateOf(false) }
	val color by animateColorAsState(
		targetValue = if (selected) {
			MaterialTheme.colorScheme.secondary
		} else {
			MaterialTheme.colorScheme.secondaryContainer
		},
		label = "Surface Color"
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
				text = element.name,
				textAlign = TextAlign.Justify,
				style = MaterialTheme.typography.titleSmall
			)

			Text(
				text = element.value,
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
