package com.space.element.domain.use_case.element_list

import com.space.element.domain.model.Element
import com.space.element.domain.repository.ElementRepository
import javax.inject.Inject

class DeleteElement @Inject constructor(private val repository: ElementRepository) {
	suspend operator fun invoke(/*context: Context,*/ element: Element) {
		repository.removeElementFromList(/*context,*/ element)
	}
}
