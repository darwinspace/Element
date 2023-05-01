package com.space.element.presentation.main.component.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.space.element.R
import com.space.element.presentation.component.ElementButton
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ElementListMode.Create
import com.space.element.presentation.theme.ElementTheme

@Preview
@Composable
fun ElementListHeaderPreview() {
	ElementTheme {
		// ElementListHeader()
	}
}

@Composable
fun ElementListHeader(
	mode: ElementListMode,
	onModeChange: (ElementListMode) -> Unit
) {
	Box {
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			ElementListHeaderContent(
				mode = mode,
				onModeChange = onModeChange
			)
		}
	}

	AnimatedVisibility(
		visible = mode is Create,
		enter = expandVertically(),
		exit = shrinkVertically(),
	) {
		CreateElementForm()
	}
}

@Composable
fun RowScope.ElementListHeaderContent(
	mode: ElementListMode,
	onModeChange: (ElementListMode) -> Unit
) {
	CreateElementButton(
		modifier = Modifier.weight(1f),
		onClick = {
			onModeChange(Create)
		}
	)

//	AnimatedVisibility(visible = Random.nextBoolean()) {
//		Row {
//			Spacer(modifier = Modifier.requiredWidth(24.dp))
//
//			ElementIconButton(onClick = { /*TODO*/ }) {
//				Icon(imageVector = Icons.Default.Search, contentDescription = null)
//			}
//		}
//	}
}

@Composable
private fun CreateElementForm(
) {
	val elementName: String = ""
	val onElementNameChange: (String) -> Unit = {}
	val elementValue: String = ""
	val onElementValueChange: (String) -> Unit = {}
	Column {
		CreateElementTextField(
			modifier = Modifier.fillMaxWidth(),
			value = elementName,
			onValueChange = onElementNameChange,
			placeholder = {
				Text(text = stringResource(R.string.element_name))
			},
			keyboardOptions = KeyboardOptions(
				capitalization = KeyboardCapitalization.Sentences,
				imeAction = ImeAction.Next
			)
		)

		Spacer(modifier = Modifier.requiredHeight(24.dp))

		CreateElementTextField(
			modifier = Modifier.fillMaxWidth(),
			value = elementValue,
			onValueChange = onElementValueChange,
			placeholder = {
				Text(text = stringResource(R.string.element_value))
			},
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Decimal,
				imeAction = ImeAction.Done
			)
		)
	}
}

@Composable
private fun CreateElementButton(
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	onClick: () -> Unit
) {
	ElementButton(
		modifier = modifier,
		text = stringResource(R.string.button_add_element),
		enabled = enabled,
		onClick = onClick
	)
}

