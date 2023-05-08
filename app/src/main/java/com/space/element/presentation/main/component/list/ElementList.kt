package com.space.element.presentation.main.component.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.space.element.R
import com.space.element.domain.model.Element
import com.space.element.presentation.component.ElementTextField
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ElementListMode.Create
import com.space.element.presentation.main.model.ElementListMode.Normal
import com.space.element.presentation.main.model.ElementListMode.Search
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
			elementListMode = Normal,
			onElementListModeChange = { throw NotImplementedError() },
			onElementListItemClick = { throw NotImplementedError() },
			onAddElementList = { _, _ -> }
		)
	}
}

@Composable
fun ElementList(
	modifier: Modifier = Modifier,
	elementList: List<Element>,
	elementListMode: ElementListMode,
	onAddElementList: (String, String) -> Unit,
	onElementListModeChange: (ElementListMode) -> Unit,
	onElementListItemClick: (Element) -> Unit
) {
	var searchValue by rememberSaveable(elementListMode is Search) {
		mutableStateOf(String())
	}

	var elementName by rememberSaveable(elementListMode is Create) {
		mutableStateOf(String())
	}

	var elementValue by rememberSaveable(elementListMode is Create) {
		mutableStateOf(String())
	}

	val isValidElementName: (String) -> Boolean = { name ->
		name.isNotBlank() && elementList.none {
			it.name == name.trim()
		}
	}

	val isValidElementValue: (String) -> Boolean = { value ->
		value.toDoubleOrNull() != null
	}

	val isValidElement: (String, String) -> Boolean = { name, value ->
		isValidElementName(name) && isValidElementValue(value)
	}


	val createElementButtonEnabled = when (elementListMode) {
		Create -> isValidElement(elementName, elementValue)
		Normal -> true
		Search -> false
	}

	Surface(modifier = modifier) {
		Column {
			ElementListHeader(
				mode = elementListMode,
				onModeChange = onElementListModeChange,
				createElementButtonEnabled = createElementButtonEnabled,
				onCreateElementClick = {
					if (elementListMode is Create) {
						onAddElementList(elementName, elementValue)
						onElementListModeChange(Normal)
					} else {
						onElementListModeChange(Create)
					}
				}
			)

			AnimatedVisibility(visible = elementListMode is Create) {
				CreateElementForm(
					elementName = elementName,
					onElementNameChange = { elementName = it },
					elementValue = elementValue,
					onElementValueChange = { elementValue = it }
				)
			}

			AnimatedVisibility(visible = elementListMode is Search) {
				SearchElementTextField(
					value = searchValue,
					onValueChange = { searchValue = it }
				)
			}

			AnimatedVisibility(visible = elementList.isNotEmpty()) {
				ElementListContent(
					elementList = elementList,
					onClick = onElementListItemClick
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
}

@Composable
fun SearchElementTextField(
	value: String,
	onValueChange: (String) -> Unit
) {
	Box(modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)) {
		ElementTextField(
			modifier = Modifier.fillMaxWidth(),
			value = value,
			onValueChange = onValueChange,
			placeholder = {
				Text(
					text = stringResource(R.string.search),
					style = MaterialTheme.typography.bodyMedium
				)
			},
			trailingIcon = {
				Icon(imageVector = Icons.Default.Search, contentDescription = null)
			},
			keyboardOptions = KeyboardOptions(
				imeAction = ImeAction.Search
			)
		)
	}
}
