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
import androidx.compose.ui.unit.dp
import com.space.element.R
import com.space.element.domain.model.Element
import com.space.element.presentation.main.*
import com.space.element.presentation.main.model.ElementListState

@Composable
fun ElementList(
	modifier: Modifier = Modifier,
	elements: List<Element>,
	elementsState: ElementListState
) {
	val searchState = elementsState is ElementListState.SearchState
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

	var elementName by rememberSaveable(elementsState is ElementListState.AddState) {
		mutableStateOf(String())
	}

	var elementValue by rememberSaveable(elementsState is ElementListState.AddState) {
		mutableStateOf(String())
	}

	val elementNameValid = {
		elementName.isNotBlank() && elements.none {
			it.name == elementName.trim()
		}
	}

	val addElementEnabled = if (elementsState is ElementListState.AddState) {
		elementNameValid() && elementValue.isNotBlank()
	} else {
		true
	}


	Column(modifier = modifier) {
		Box(modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)) {
			ElementListHeader(
				state = elementsState,
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
		}

		/*
		if (elements.isEmpty()) {
			ElementEmptyListCard()
		}

		if (filteredElements.isEmpty() && elements.isNotEmpty()) {
			ElementEmptySearchListCard()
		}
		*/

		ElementListContent(elements) { element ->
			// viewModel.onElementItemClick(element)
		}
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
