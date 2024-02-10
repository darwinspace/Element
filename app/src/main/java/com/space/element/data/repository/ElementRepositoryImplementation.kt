package com.space.element.data.repository

import com.space.element.data.source.ElementDatabase
import com.space.element.domain.model.Element
import com.space.element.domain.repository.ElementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ElementRepositoryImplementation @Inject constructor(
	private val database: ElementDatabase
) : ElementRepository {
	override fun getList(): Flow<List<Element>> {
		return database.getList()
	}

	override suspend fun add(element: Element) {
		database.add(element)
	}

	override suspend fun remove(element: Element) {
		database.remove(element)
	}
}
