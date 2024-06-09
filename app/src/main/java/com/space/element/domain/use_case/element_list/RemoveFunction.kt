package com.space.element.domain.use_case.element_list

import com.space.element.domain.model.Function
import com.space.element.domain.repository.FunctionRepository
import javax.inject.Inject

class RemoveFunction @Inject constructor(private val repository: FunctionRepository) {
	suspend operator fun invoke(function: Function) {
		repository.remove(function)
	}
}
