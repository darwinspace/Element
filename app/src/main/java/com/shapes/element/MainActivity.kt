package com.shapes.element

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapes.element.ui.theme.ElementTheme
import com.shapes.element.ui.theme.elementBorder

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ElementTheme {
				App()
			}
		}
	}
}

@ExperimentalMaterialApi
@Composable
fun App() {
	Surface(
		modifier = Modifier.fillMaxSize(),
		color = MaterialTheme.colors.background
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			Box(
				modifier = Modifier
					.background(Color(0xFFE6E6E6))
					.height(128.dp)
					.fillMaxWidth()
			)

			Column {
				ElementDivider()
				KeyboardElements()
				ElementDivider()
				Keyboard()
			}
		}
	}
}

@ExperimentalMaterialApi
@Composable
fun KeyboardElementItem(elementName: String) {
	LazyColumn(content = {})
	Surface(
		onClick = { /*TODO*/ }, shape = MaterialTheme.shapes.small,
		border = BorderStroke(2.dp, color = MaterialTheme.colors.elementBorder)
	) {
		Box(
			modifier = Modifier.padding(12.dp, 8.dp)
		) {
			KeyboardElementItemName(elementName)
		}
	}
}

val numbers: List<Pair<String, (String) -> String>> = (0..9).map { number ->
	val numberValue = number.toString()
	val second: (String) -> String = {
		var result = it

		if (it.lastOrNull()?.isDigit() != false) {
			result = "$it "
		}

		result + numberValue
	}

	numberValue to second
}

val operators = listOf<Pair<String, (String) -> String>>(
	"<-" to { text ->
		if (text.isNotEmpty()) text.dropLast(text.length) else text
	},
	"(" to { text ->
		"$text ("
	},
	")" to { text ->
		"$text )"
	},
	"/" to { text ->
		"$text /"
	},
	"+" to { text ->
		"$text +"
	},
	"-" to { text ->
		"$text -"
	},
	"*" to { text ->
		"$text *"
	},
	"." to { text ->
		"$text ."
	}
)

@Composable
fun KeyboardElementItemName(elementName: String) {
	Text(text = elementName, style = MaterialTheme.typography.caption)
}

@ExperimentalMaterialApi
@Composable
fun KeyboardElements() {
	LazyRow(
		modifier = Modifier.fillMaxWidth(),
		contentPadding = PaddingValues(12.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		items(5) {
			KeyboardElementItem("Item number ${it + 1}")
		}
	}
}

@ExperimentalMaterialApi
@Composable
fun Keyboard() {
	Column(
		modifier = Modifier
			.background(MaterialTheme.colors.surface)
			.fillMaxWidth()
	) {
		Row {
			operators.slice(0..3).forEach { (text, function) ->
				KeyboardButton(onClick = { }) {
					KeyboardButtonText(text)
				}
			}
		}

		Row {
			numbers.slice(1..3).forEach { (text, function) ->
				KeyboardButton(onClick = { /* TODO */ }) {
					KeyboardButtonText(text)
				}
			}

			operators[4].let { (text, function) ->
				KeyboardButton(onClick = { /* TODO */ }) {
					KeyboardButtonText(text)
				}
			}
		}

		Row {
			numbers.slice(4..6).forEach { (text, function) ->
				KeyboardButton(onClick = { /* TODO */ }) {
					KeyboardButtonText(text)
				}
			}

			operators[5].let { (text, function) ->
				KeyboardButton(onClick = { /* TODO */ }) {
					KeyboardButtonText(text)
				}
			}
		}

		Row {
			numbers.slice(7..9).forEach { (text, function) ->
				KeyboardButton(onClick = { /* TODO */ }) {
					KeyboardButtonText(text)
				}
			}

			operators[6].let { (text, function) ->
				KeyboardButton(onClick = { /* TODO */ }) {
					KeyboardButtonText(text)
				}
			}
		}

		Row {
			numbers[0].let { (text, function) ->
				KeyboardButton(weight = 2f, onClick = { /* TODO */ }) {
					KeyboardButtonText(text)
				}
			}

			operators[7].let { (text, function) ->
				KeyboardButton(onClick = { /* TODO */ }) {
					KeyboardButtonText(text)
				}
			}

			KeyboardButton(onClick = { /* TODO */ }) {
				KeyboardButtonText("=")
			}
		}
	}
}

@Composable
fun KeyboardButtonText(text: String) {
	Text(text = text, style = MaterialTheme.typography.button)
}

@ExperimentalMaterialApi
@Composable
fun RowScope.KeyboardButton(
	weight: Float = 1f,
	onClick: () -> Unit,
	content: @Composable BoxScope.() -> Unit
) {
	Surface(
		modifier = Modifier
			.padding(8.dp)
			.weight(weight),
		onClick = onClick,
		shape = MaterialTheme.shapes.medium
	) {
		Box(
			content = content,
			modifier = Modifier.padding(16.dp),
			contentAlignment = Alignment.Center
		)
	}
}

@Composable
fun ElementDivider() {
	Divider(thickness = 2.dp, color = MaterialTheme.colors.elementBorder)
}

@ExperimentalMaterialApi
@Preview(showBackground = true, widthDp = 360)
@Composable
fun KeyboardPreview() {
	ElementTheme {
		Column {
			KeyboardElements()
			ElementDivider()
			Keyboard()
		}
	}
}
