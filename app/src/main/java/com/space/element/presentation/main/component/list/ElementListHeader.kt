package com.space.element.presentation.main.component.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.space.element.presentation.component.ElementIconButton
import com.space.element.presentation.component.ElementTextField
import com.space.element.presentation.main.model.ElementListMode
import com.space.element.presentation.main.model.ElementListMode.Create
import com.space.element.presentation.main.model.ElementListMode.Normal
import com.space.element.presentation.main.model.ElementListMode.Search
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
	onModeChange: (ElementListMode) -> Unit,
	createElementEnabled: Boolean,
	onCreateElementClick: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 24.dp, top = 8.dp, end = 24.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		AnimatedVisibility(visible = mode is Create) {
			Row {
				ElementIconButton(
					onClick = {
						onModeChange(Normal)
					}
				) {
					Icon(imageVector = Icons.Default.Close, contentDescription = null)
				}

				Spacer(modifier = Modifier.width(16.dp))
			}
		}

		CreateElementButton(
			modifier = Modifier.weight(1f),
			enabled = createElementEnabled,
			onClick = onCreateElementClick
		)

		AnimatedVisibility(visible = mode !is Create) {
			Row {
				Spacer(modifier = Modifier.width(16.dp))

				ElementIconButton(
					onClick = {
						if (mode is Normal) {
							onModeChange(Search)
						} else if (mode is Search) {
							onModeChange(Normal)
						}
					}
				) {
					AnimatedVisibility(
						visible = mode is Normal,
						enter = fadeIn(),
						exit = fadeOut()
					) {
						Icon(imageVector = Icons.Default.Search, contentDescription = null)
					}

					AnimatedVisibility(
						visible = mode is Search,
						enter = fadeIn(),
						exit = fadeOut()
					) {
						Icon(imageVector = Icons.Default.Close, contentDescription = null)
					}
				}
			}
		}
	}
}

@Composable
fun CreateElementForm(
	elementName: String,
	onElementNameChange: (String) -> Unit,
	elementValue: String,
	onElementValueChange: (String) -> Unit
) {
	Column(modifier = Modifier.padding(24.dp)) {
		ElementTextField(
			modifier = Modifier.fillMaxWidth(),
			value = elementName,
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

		Spacer(modifier = Modifier.height(24.dp))

		ElementTextField(
			modifier = Modifier.fillMaxWidth(),
			value = elementValue,
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

