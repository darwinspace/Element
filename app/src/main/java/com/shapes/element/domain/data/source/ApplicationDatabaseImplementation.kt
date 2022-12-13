package com.shapes.element.domain.data.source

import com.shapes.element.domain.model.ApplicationData
import com.shapes.element.domain.model.Element
import kotlinx.coroutines.delay

class ApplicationDatabaseImplementation : ApplicationDatabase {
	override suspend fun getApplicationData(): ApplicationData {
		val list = buildList {
			add(Element(name = "Box 🎁", value = 20.0))
			add(Element(name = "Taco 🌮", value = 25.0))
			add(Element(name = "Burrito 🌯", value = 40.0))
			add(Element(name = "Hamburger 🍔", value = 60.0))
			add(Element(name = "Coca Cola 🥤", value = 20.0))
		}

		delay(timeMillis = 0L)

		return ApplicationData(list)
	}

	override suspend fun setApplicationData(data: ApplicationData) {
		TODO("Not yet implemented")
	}
}
