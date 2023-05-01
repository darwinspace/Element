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
import androidx.compose.ui.unit.dp
import com.space.element.R
import com.space.element.domain.model.Element
import com.space.element.presentation.main.*
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ElementListPreview() {
	val elementList = List(10) {
		Element(
			name = "Item $it",
			value = it.toString()
		)
	}

	ElementTheme {
		ElementList(
			elementList = elementList,
			elementListMode = ElementListMode.Normal,
			onElementListModeChange = {}
		)
	}
}

@Composable
fun ElementList(
	modifier: Modifier = Modifier,
	elementList: List<Element>,
	elementListMode: ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit
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

	Column(
		modifier = modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		ElementListHeader(
			mode = elementListMode,
			onModeChange = onElementListModeChange
		)

		if (elementList.isNotEmpty()) {
			ElementListContent(
				elementList = elementList,
				onClick = {
					// viewModel.onElementItemClick(element)
				}
			)
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
		modifier = modifier,
		value = value,
		onValueChange = onValueChange,
		placeholder = placeholder,
		keyboardOptions = keyboardOptions,
		singleLine = true,
		shape = shape
	)
}
