package com.space.element.domain.use_case.function_list

import com.space.element.domain.model.Function
import com.space.element.domain.repository.FunctionRepository
import javax.inject.Inject

class AddFunction @Inject constructor(private val repository: FunctionRepository) {
	suspend operator fun invoke(function: Function) {
		repository.add(function)
	}
}
