package com.shapes.element.domain.data.repository

import android.content.Context
import com.shapes.element.domain.data.source.ElementDatabase
import com.shapes.element.domain.data.source.ElementDatabaseImplementation
import com.shapes.element.domain.model.Element
import com.shapes.element.domain.repository.ElementRepository
import kotlinx.coroutines.flow.Flow

class ElementRepositoryImplementation(
	private val database: ElementDatabase = ElementDatabaseImplementation()
) : ElementRepository {
	override fun getElementList(context: Context): Flow<List<Element>> {
		return database.getList(context)
	}

	override suspend fun addElementToList(context: Context, element: Element) {
		database.addElementToList(context, element)
	}

	override suspend fun editElementFromList(context: Context, element: Element) {
		database.editElementFromList(context, element)
	}

	override suspend fun removeElementFromList(context: Context, element: Element) {
		database.removeElementFromList(context, element)
	}
}
