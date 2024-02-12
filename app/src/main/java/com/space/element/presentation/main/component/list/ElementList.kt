package com.space.element.presentation.main.component.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
			elementListMode = { elementListMode },
			onElementListModeChange = { elementListMode = it },
			onElementListItemClick = { throw NotImplementedError() },
			onElementListItemLongClick = { throw NotImplementedError() },
			elementListQuery = { elementListQuery },
			onElementListQueryChange = { elementListQuery = it },
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
	onElementListItemLongClick: (Element) -> Unit,
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
				isCreateElementButtonEnabled = isCreateElementButtonEnabled,
				isElementListEmpty = list.isEmpty(),
				onCreateElementClick = onCreateElementClick
			)

			AnimatedVisibility(visible = mode is Create) {
				CreateElementForm(
					elementName = elementName,
					onElementNameChange = onElementNameChange,
					elementValue = elementValue,
					onElementValueChange = onElementValueChange
				)
			}

			AnimatedVisibility(visible = mode is Search) {
				SearchElementTextField(
					value = elementListQuery,
					onValueChange = onElementListQueryChange
				)
			}

			AnimatedVisibility(visible = list.isEmpty()) {
				EmptyElementListCard()
			}

			AnimatedVisibility(visible = list.isNotEmpty()) {
				ElementListContent(
					elementList = list,
					onLongClick = onElementListItemLongClick,
					onClick = onElementListItemClick
				)
			}
		}
	}
}

@Composable
fun SearchElementTextField(
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
