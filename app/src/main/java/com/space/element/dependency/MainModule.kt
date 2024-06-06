package com.space.element.dependency

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.space.element.data.repository.ElementRepositoryImplementation
import com.space.element.data.repository.FunctionRepositoryImplementation
import com.space.element.data.source.ElementDatabaseImplementation
import com.space.element.data.source.FunctionDatabaseImplementation
import com.space.element.domain.data.ElementDatabase
import com.space.element.domain.data.FunctionDatabase
import com.space.element.domain.repository.ElementRepository
import com.space.element.domain.repository.FunctionRepository
import com.space.element.domain.use_case.element_list.AddElement
import com.space.element.domain.use_case.element_list.GetElementList
import com.space.element.domain.use_case.element_list.RemoveElement
import com.space.element.domain.use_case.expression.EvaluateExpression
import com.space.element.domain.use_case.function_list.GetFunctionList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {
	private val Context.elementListDataStore by preferencesDataStore("ElementListDataStore")
	private val Context.functionListDataStore by preferencesDataStore("FunctionListDataStore")

	@Provides
	@Singleton
	fun provideElementDatabase(application: Application): ElementDatabase {
		return ElementDatabaseImplementation(application.elementListDataStore)
	}

	@Provides
	@Singleton
	fun provideElementRepository(database: ElementDatabase): ElementRepository {
		return ElementRepositoryImplementation(database)
	}

	@Provides
	@Singleton
	fun provideGetElementList(repository: ElementRepository): GetElementList {
		return GetElementList(repository)
	}

	@Provides
	@Singleton
	fun provideAddToElementList(repository: ElementRepository): AddElement {
		return AddElement(repository)
	}

	@Provides
	@Singleton
	fun provideRemoveElementFromList(repository: ElementRepository): RemoveElement {
		return RemoveElement(repository)
	}

	@Provides
	@Singleton
	fun provideExecuteExpression(): EvaluateExpression {
		return EvaluateExpression()
	}

	@Provides
	@Singleton
	fun provideFunctionDatabase(application: Application): FunctionDatabase {
		return FunctionDatabaseImplementation(application.functionListDataStore)
	}

	@Provides
	@Singleton
	fun provideFunctionRepository(database: FunctionDatabase): FunctionRepository {
		return FunctionRepositoryImplementation(database)
	}

	@Provides
	@Singleton
	fun provideGetFunctionList(repository: FunctionRepository): GetFunctionList {
		return GetFunctionList(repository)
	}
}
