package com.shapes.element.domain.data.source

import com.shapes.element.domain.model.ApplicationData

interface ApplicationDatabase {
	suspend fun getApplicationData(): ApplicationData
	suspend fun setApplicationData(data: ApplicationData)
}
