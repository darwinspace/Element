package com.space.element.data.source

import com.space.element.domain.model.Element
import kotlinx.coroutines.flow.Flow

interface ElementDatabase {
	fun getList(): Flow<List<Element>>
	suspend fun add(element: Element)
	suspend fun remove(element: Element)
}
