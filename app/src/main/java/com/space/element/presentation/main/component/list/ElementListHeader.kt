package com.space.element.presentation.main.component.list

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledTonalIconButton
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
import androidx.compose.ui.unit.dp
import com.space.element.R
import com.space.element.presentation.component.ElementButton
import com.space.element.presentation.main.component.list.CreateElementTextField
import com.space.element.presentation.main.component.list.SearchElementTextField
import com.space.element.presentation.main.model.ElementListState


@Composable
fun ElementListHeader(
	state: ElementListState,
	onStateChange: (ElementListState) -> Unit,
	addElementEnabled: Boolean,
	searchValue: String,
	onSearchValueChange: (String) -> Unit,
	elementName: String,
	onElementNameChange: (String) -> Unit,
	elementValue: String,
	onElementValueChange: (String) -> Unit,
	onAddElement: () -> Unit
) {
	val isIdleMode = state is ElementListState.IdleState
	val isAddMode = state is ElementListState.AddState
	val isSearchMode = state is ElementListState.SearchState

	val height = 48.dp

	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		AnimatedVisibility(visible = isAddMode) {
			FilledTonalIconButton(
				modifier = Modifier.size(height),
				onClick = {
					onStateChange(ElementListState.IdleState)
				}
			) {
				Icon(imageVector = Icons.Default.Close, contentDescription = null)
			}
		}

		AnimatedVisibility(
			visible = isAddMode
		) {
			Spacer(modifier = Modifier.requiredWidth(24.dp))
		}

		AnimatedVisibility(
			visible = isIdleMode || isAddMode || isSearchMode,
			modifier = Modifier.weight(1f)
		) {
			AnimatedVisibility(
				visible = isIdleMode || isAddMode,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				CreateItemButton(
					modifier = Modifier.heightIn(height),
					enabled = addElementEnabled
				) {
					if (isIdleMode) {
						onStateChange(ElementListState.AddState)
					}

					if (isAddMode) {
						onAddElement()
					}
				}
			}

			AnimatedVisibility(
				visible = isSearchMode,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				SearchElementTextField(searchValue, onSearchValueChange)
			}
		}

		AnimatedVisibility(visible = isIdleMode || isSearchMode) {
			Spacer(modifier = Modifier.requiredWidth(24.dp))
		}

		AnimatedVisibility(visible = isIdleMode || isSearchMode || isAddMode) {
			FilledTonalIconButton(
				modifier = Modifier
					.border(
						BorderStroke(
							width = 2.dp,
							MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
						), CircleShape
					)
					.size(height),
				onClick = {
					val s =
						if (isIdleMode)
							ElementListState.SearchState
						else
							ElementListState.IdleState
					onStateChange(s)
				}
			) {
				AnimatedVisibility(
					visible = isIdleMode,
					enter = fadeIn(),
					exit = fadeOut()
				) {
					Icon(imageVector = Icons.Default.Search, contentDescription = null)
				}

				AnimatedVisibility(
					visible = isSearchMode || isAddMode,
					enter = fadeIn(),
					exit = fadeOut()
				) {
					Icon(imageVector = Icons.Default.Close, contentDescription = null)
				}
			}
		}
	}

	AnimatedVisibility(
		visible = isAddMode,
		enter = expandVertically(),
		exit = shrinkVertically(),
	) {
		CreateElementForm(elementName, onElementNameChange, elementValue, onElementValueChange)
	}
}

@Composable
private fun CreateElementForm(
	elementName: String,
	onElementNameChange: (String) -> Unit,
	elementValue: String,
	onElementValueChange: (String) -> Unit
) {
	Column {
		Spacer(modifier = Modifier.requiredHeight(24.dp))

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
private fun CreateItemButton(
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

