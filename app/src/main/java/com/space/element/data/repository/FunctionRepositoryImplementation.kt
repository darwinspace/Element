package com.space.element.data.repository

import com.space.element.domain.data.FunctionDatabase
import com.space.element.domain.model.Function
import com.space.element.domain.repository.FunctionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FunctionRepositoryImplementation @Inject constructor(
	private val database: FunctionDatabase
) :	FunctionRepository {
	override fun getList(): Flow<List<Function>> {
		return database.getList()
	}

	override suspend fun add(function: Function) {
		database.add(function)
	}
}