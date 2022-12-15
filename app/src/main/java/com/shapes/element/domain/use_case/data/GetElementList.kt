package com.shapes.element.domain.use_case.data

import android.content.Context
import com.shapes.element.domain.model.Element
import com.shapes.element.domain.repository.ElementRepository
import kotlinx.coroutines.flow.Flow

class GetElementList(private val repository: ElementRepository) {
	operator fun invoke(context: Context): Flow<List<Element>> {
		return repository.getElementList(context)
	}
}
