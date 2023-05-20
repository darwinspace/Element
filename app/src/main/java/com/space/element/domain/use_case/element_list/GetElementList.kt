package com.space.element.domain.use_case.element_list

import com.space.element.domain.model.Element
import com.space.element.domain.repository.ElementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetElementList @Inject constructor(private val repository: ElementRepository) {
	operator fun invoke(): Flow<List<Element>> {
		return repository.getList()
	}
}
