package com.space.element.presentation.main.component.list

import android.content.res.Configuration
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

@Composable
private fun getElementListPreview(): List<Element> {
	return List(10) {
		Element(name = "Item $it", value = it.toString())
	}
}

@Preview(
	device = "spec:width=360dp,height=640dp",
	uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ElementListPreview() {
	val elementList = getElementListPreview()
	var elementListMode by remember { mutableStateOf<ElementListMode>(ElementListMode.Normal) }
	var elementListQuery by remember { mutableStateOf(String()) }
	var elementName by remember { mutableStateOf(String()) }
	var elementValue by remember { mutableStateOf(String()) }

	ElementTheme {
		ElementList(
			elementList = elementList,
			elementListMode = elementListMode,
			onElementListModeChange = { elementListMode = it },
			onElementListItemClick = { throw NotImplementedError() },
			onElementListItemLongClick = { throw NotImplementedError() },
			elementListQuery = elementListQuery,
			onElementListQueryChange = { elementListQuery = it },
			elementName = elementName,
			onElementNameChange = { elementName = it },
			elementValue = elementValue,
			onElementValueChange = { elementValue = it },
			createElementEnabled = true,
			onCreateElementClick = { throw NotImplementedError() }
		)
	}
}

@Composable
fun ElementList(
	modifier: Modifier = Modifier,
	elementList: List<Element>,
	onElementListItemClick: (Element) -> Unit,
	onElementListItemLongClick: (Element) -> Unit,
	elementListMode: ElementListMode,
	onElementListModeChange: (ElementListMode) -> Unit,
	elementListQuery: String,
	onElementListQueryChange: (String) -> Unit,
	elementName: String,
	onElementNameChange: (String) -> Unit,
	elementValue: String,
	onElementValueChange: (String) -> Unit,
	createElementEnabled: Boolean,
	onCreateElementClick: () -> Unit
) {
	Surface(modifier = modifier) {
		Column {
			ElementListHeader(
				mode = elementListMode,
				onModeChange = onElementListModeChange,
				createElementEnabled = createElementEnabled,
				onCreateElementClick = onCreateElementClick
			)

			AnimatedVisibility(visible = elementListMode is Create) {
				CreateElementForm(
					elementName = elementName,
					onElementNameChange = onElementNameChange,
					elementValue = elementValue,
					onElementValueChange = onElementValueChange
				)
			}

			AnimatedVisibility(visible = elementListMode is Search) {
				SearchElementTextField(
					value = elementListQuery,
					onValueChange = onElementListQueryChange
				)
			}

			AnimatedVisibility(visible = elementList.isEmpty()) {
				EmptyElementListCard()
			}

			AnimatedVisibility(visible = elementList.isNotEmpty()) {
				ElementListContent(
					elementList = elementList,
					onClick = onElementListItemClick,
					onLongClick = onElementListItemLongClick
				)
			}
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
				Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
			},
			keyboardOptions = KeyboardOptions(
				imeAction = ImeAction.Search
			)
		)
	}
}
