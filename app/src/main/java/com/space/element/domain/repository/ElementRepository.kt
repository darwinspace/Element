package com.space.element.domain.repository

import com.space.element.domain.model.Element
import kotlinx.coroutines.flow.Flow

interface ElementRepository {
	fun getList(): Flow<List<Element>>
	suspend fun add(element: Element)
	suspend fun remove(element: Element)
}
