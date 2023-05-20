package com.space.element.dependency

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.space.element.domain.data.repository.ElementRepositoryImplementation
import com.space.element.domain.data.source.ElementDatabase
import com.space.element.domain.data.source.ElementDatabaseImplementation
import com.space.element.domain.repository.ElementRepository
import com.space.element.domain.use_case.element_list.AddElement
import com.space.element.domain.use_case.element_list.GetElementList
import com.space.element.domain.use_case.element_list.RemoveElement
import com.space.element.domain.use_case.expression.EvaluateExpression
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {
	private val Context.dataStore by preferencesDataStore("ApplicationElementList")

	@Provides
	@Singleton
	fun provideDataStore(application: Application): DataStore<Preferences> {
		return application.dataStore
	}

	@Provides
	@Singleton
	fun provideDatabase(dataStore: DataStore<Preferences>): ElementDatabase {
		return ElementDatabaseImplementation(dataStore)
	}

	@Provides
	@Singleton
	fun provideRepository(database: ElementDatabase): ElementRepository {
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
}
