package com.space.element.domain.data.source

import com.space.element.domain.model.Element
import kotlinx.coroutines.flow.Flow

interface ElementDatabase {
	fun getList(): Flow<List<Element>>
	suspend fun addElementToList(element: Element)
	suspend fun editElementFromList(element: Element)
	suspend fun removeElementFromList(element: Element)
}
