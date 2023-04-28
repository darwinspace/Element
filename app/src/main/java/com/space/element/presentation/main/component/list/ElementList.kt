package com.space.element.presentation.main.component.list

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.space.element.R
import com.space.element.domain.model.Element
import com.space.element.presentation.main.*
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.theme.ElementTheme
import kotlin.random.Random

@Preview
@Composable
fun ElementListPreview() {
	val elementList = remember {
		buildList<Element> {
			repeat(1) {
				add(
					Element(
						name = "Object ${it + 1}",
						value = Random.nextInt(10, 20).toString()
					)
				)
			}
		}
	}

	ElementTheme {
		ElementList(
			elementList = elementList,
			elementListMode = ElementListMode.Create
		)
	}
}

@Composable
fun ElementList(
	modifier: Modifier = Modifier,
	elementList: List<Element>,
	elementListMode: ElementListMode
) {
	val searchState = elementListMode is ElementListMode.Search
	var searchValue by rememberSaveable(searchState) {
		mutableStateOf(String())
	}

	/*
	val elementFilteredList = remember(searchState) {
		elementList.filterIf(searchState && search.isNotBlank()) { (name) ->
			name.contains(search, ignoreCase = true)
		}
	}
	*/

	var elementName by rememberSaveable(elementListMode is ElementListMode.Create) {
		mutableStateOf(String())
	}

	var elementValue by rememberSaveable(elementListMode is ElementListMode.Create) {
		mutableStateOf(String())
	}

	val elementNameValid = {
		elementName.isNotBlank() && elementList.none {
			it.name == elementName.trim()
		}
	}

	val addElementEnabled = if (elementListMode is ElementListMode.Create) {
		elementNameValid() && elementValue.isNotBlank()
	} else {
		true
	}

	Column(modifier = modifier) {
		ElementListHeader(
			mode = elementListMode,
			onStateChange = {
				//viewModel.elementsState = it
			},
			addElementEnabled = addElementEnabled,
			searchValue = searchValue,
			onSearchValueChange = { searchValue = it },
			elementName = elementName,
			onElementNameChange = { elementName = it },
			elementValue = elementValue,
			onElementValueChange = { elementValue = it },
			onAddElement = {
				// viewModel.addElement(elementName, elementValue)
			}
		)

		if (elementList.isNotEmpty()) {
			ElementListContent(elementList) {
				// viewModel.onElementItemClick(element)
			}
		}

		if (elementList.isEmpty()) {
			EmptyElementListCard()
		}

		/*
		if (filteredElements.isEmpty() && elements.isNotEmpty()) {
			ElementEmptySearchListCard()
		}
		*/
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchElementTextField(
	value: String,
	onValueChange: (String) -> Unit
) {
	OutlinedTextField(
		value = value,
		onValueChange = onValueChange,
		placeholder = {
			Text(text = stringResource(R.string.search))
		},
		keyboardOptions = KeyboardOptions(
			imeAction = ImeAction.Search
		),
		singleLine = true,
		shape = MaterialTheme.shapes.small
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateElementTextField(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	placeholder: @Composable (() -> Unit),
	keyboardOptions: KeyboardOptions
) {
	val shape = MaterialTheme.shapes.small

	OutlinedTextField(
		value = value,
		onValueChange = onValueChange,
		placeholder = placeholder,
		keyboardOptions = keyboardOptions,
		singleLine = true,
		shape = shape
	)
}
