package com.space.element.domain.repository

import com.space.element.domain.model.Function
import kotlinx.coroutines.flow.Flow

interface FunctionRepository {
	fun getList(): Flow<List<Function>>
}
