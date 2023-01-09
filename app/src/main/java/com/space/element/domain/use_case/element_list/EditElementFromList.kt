package com.space.element.domain.use_case.element_list

import com.space.element.domain.model.Element
import com.space.element.domain.repository.ElementRepository
import javax.inject.Inject

class EditElementFromList @Inject constructor(private val repository: ElementRepository) {
	suspend operator fun invoke(element: Element) {
		repository.editElementFromList(element)
	}
}
