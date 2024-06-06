package com.space.element.domain.use_case.function_list

import com.space.element.domain.model.Function
import com.space.element.domain.repository.FunctionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFunctionList @Inject constructor(private val repository: FunctionRepository) {
	operator fun invoke(): Flow<List<Function>> {
		return repository.getList()
	}
}