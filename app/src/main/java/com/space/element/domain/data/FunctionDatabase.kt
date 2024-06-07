package com.space.element.domain.data

import com.space.element.domain.model.Function
import kotlinx.coroutines.flow.Flow

interface FunctionDatabase {
	fun getList(): Flow<List<Function>>
	suspend fun add(function: Function)
}
