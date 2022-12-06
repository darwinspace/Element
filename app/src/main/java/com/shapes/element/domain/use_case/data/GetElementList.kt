package com.shapes.element.domain.use_case.data

import com.shapes.element.domain.data.repository.ApplicationRepositoryImplementation
import com.shapes.element.domain.model.ElementItemData
import com.shapes.element.domain.repository.ApplicationRepository

class GetElementList(private val repository: ApplicationRepository = ApplicationRepositoryImplementation()) {
	suspend operator fun invoke(): List<ElementItemData> {
		return emptyList()
	}
}
