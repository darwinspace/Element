package com.shapes.element.domain.repository

import android.content.Context
import com.shapes.element.domain.model.Element
import kotlinx.coroutines.flow.Flow

interface ElementRepository {
	fun getElementList(context: Context): Flow<List<Element>>
	suspend fun setElementList(context: Context, data: List<Element>)
}
