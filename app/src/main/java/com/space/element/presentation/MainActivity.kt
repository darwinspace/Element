package com.space.element.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.graphics.Color
import com.space.element.presentation.main.MainScreen
import com.space.element.ui.theme.ElementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val colorScheme = MaterialTheme.colorScheme.asString()
			Log.d("ColorScheme", colorScheme)

			ElementTheme {
				val size = calculateWindowSizeClass(this)
				MainScreen(size)
			}
		}
	}

	private fun Color.asString(): String {
		val r = value shr 48 and 0xffUL
		val g = value shr 40 and 0xffUL
		val b = value shr 32 and 0xffUL
		return "($r, $g, $b)"
	}

	private fun ColorScheme.asString(): String {
		return "ColorScheme(" +
				"primary=${primary.asString()}" +
				", onPrimary=${onPrimary.asString()}" +
				", primaryContainer=${primaryContainer.asString()}" +
				", onPrimaryContainer=${onPrimaryContainer.asString()}" +
				", inversePrimary=${inversePrimary.asString()}" +
				", secondary=${secondary.asString()}" +
				", onSecondary=${onSecondary.asString()}" +
				", secondaryContainer=${secondaryContainer.asString()}" +
				", onSecondaryContainer=${onSecondaryContainer.asString()}" +
				", tertiary=${tertiary.asString()}" +
				", onTertiary=${onTertiary.asString()}" +
				", tertiaryContainer=${tertiaryContainer.asString()}" +
				", onTertiaryContainer=${onTertiaryContainer.asString()}" +
				", background=${background.asString()}" +
				", onBackground=${onBackground.asString()}" +
				", surface=${surface.asString()}" +
				", onSurface=${onSurface.asString()}" +
				", surfaceVariant=${surfaceVariant.asString()}" +
				", onSurfaceVariant=${onSurfaceVariant.asString()}" +
				", surfaceTint=${surfaceTint.asString()}" +
				", inverseSurface=${inverseSurface.asString()}" +
				", inverseOnSurface=${inverseOnSurface.asString()}" +
				", error=${error.asString()}" +
				", onError=${onError.asString()}" +
				", errorContainer=${errorContainer.asString()}" +
				", onErrorContainer=${onErrorContainer.asString()}" +
				", outline=${outline.asString()}" +
				", outlineVariant=${outlineVariant.asString()}" +
				", scrim=${scrim.asString()})"
	}
}
