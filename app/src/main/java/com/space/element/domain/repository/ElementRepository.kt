package com.space.element.domain.repository

import com.space.element.domain.model.Element
import kotlinx.coroutines.flow.Flow

interface ElementRepository {
	fun getElementList(): Flow<List<Element>>
	suspend fun addElementToList(element: Element)
	suspend fun editElementFromList(element: Element)
	suspend fun removeElementFromList(element: Element)
}
