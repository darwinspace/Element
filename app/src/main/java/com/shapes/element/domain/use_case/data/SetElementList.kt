package com.shapes.element.domain.use_case.data

import android.content.Context
import com.shapes.element.domain.model.ElementItemData
import com.shapes.element.domain.repository.ApplicationRepository

class SetElementList(private val repository: ApplicationRepository) {
	suspend operator fun invoke(context: Context, elementList: List<ElementItemData>) {
		repository.setElementList(context, elementList)
	}
}
