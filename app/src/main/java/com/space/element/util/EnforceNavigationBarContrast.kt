package com.space.element.util

import android.os.Build
import androidx.activity.ComponentActivity

fun ComponentActivity.enforceNavigationBarContrast() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
		window.isNavigationBarContrastEnforced = false
	}
}
