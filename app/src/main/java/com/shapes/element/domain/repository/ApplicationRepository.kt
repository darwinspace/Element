package com.shapes.element.domain.repository

import android.content.Context
import com.shapes.element.domain.model.ElementItemData
import kotlinx.coroutines.flow.Flow

interface ApplicationRepository {
	fun getElementList(context:Context): Flow<List<ElementItemData>>
	suspend fun setElementList(context:Context, data: List<ElementItemData>)
}
