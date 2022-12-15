package com.shapes.element.domain.use_case.data

import android.content.Context
import com.shapes.element.domain.model.Element
import com.shapes.element.domain.repository.ElementRepository

class EditElementFromList(private val repository: ElementRepository) {
	suspend operator fun invoke(context: Context, element: Element) {
		repository.editElementFromList(context, element)
	}
}
