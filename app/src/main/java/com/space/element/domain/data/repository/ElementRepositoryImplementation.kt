package com.space.element.domain.data.repository

import com.space.element.domain.data.source.ElementDatabase
import com.space.element.domain.model.Element
import com.space.element.domain.repository.ElementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ElementRepositoryImplementation @Inject constructor(
	private val database: ElementDatabase
) : ElementRepository {
	override fun getElementList(): Flow<List<Element>> {
		return database.getList()
	}

	override suspend fun addElementToList(element: Element) {
		database.addElementToList(element)
	}

	override suspend fun editElementFromList(element: Element) {
		database.editElementFromList(element)
	}

	override suspend fun removeElementFromList(element: Element) {
		database.removeElementFromList(element)
	}
}
