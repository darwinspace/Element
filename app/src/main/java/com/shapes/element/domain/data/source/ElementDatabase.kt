package com.shapes.element.domain.data.source

import android.content.Context
import com.shapes.element.domain.model.Element
import kotlinx.coroutines.flow.Flow

interface ElementDatabase {
	fun getList(context: Context): Flow<List<Element>>
	suspend fun addElementToList(context: Context, element: Element)
	suspend fun editElementFromList(context: Context, element: Element)
	suspend fun removeElementFromList(context: Context, element: Element)
}
