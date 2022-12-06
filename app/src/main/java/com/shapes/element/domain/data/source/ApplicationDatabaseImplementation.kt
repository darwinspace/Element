package com.shapes.element.domain.data.source

import com.shapes.element.domain.model.ApplicationData
import com.shapes.element.domain.model.ElementItemData
import kotlinx.coroutines.delay

class ApplicationDatabaseImplementation : ApplicationDatabase {
	override suspend fun getApplicationData(): ApplicationData {
		val list = buildList {
			add(ElementItemData(name = "Box 🎁", value = 20.0))
			add(ElementItemData(name = "Taco 🌮", value = 25.0))
			add(ElementItemData(name = "Burrito 🌯", value = 40.0))
			add(ElementItemData(name = "Hamburger 🍔", value = 60.0))
			add(ElementItemData(name = "Coca Cola 🥤", value = 20.0))
		}

		delay(timeMillis = 0L)

		return ApplicationData(list)
	}

	override suspend fun setApplicationData(data: ApplicationData) {
		TODO("Not yet implemented")
	}
}
