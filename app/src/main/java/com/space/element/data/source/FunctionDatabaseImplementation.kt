package com.space.element.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.space.element.domain.data.FunctionDatabase
import com.space.element.domain.model.Function
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FunctionDatabaseImplementation @Inject constructor(
	private val dataStore: DataStore<Preferences>
) : FunctionDatabase {
	override fun getList(): Flow<List<Function>> {
		return dataStore.data.map { preferences ->
			preferences.asMap().map { (key, value) ->
				Function(key.name, value.toString())
			}.reversed()
		}
	}

	override suspend fun add(function: Function) {
		dataStore.edit { preferences ->
			val key = stringPreferencesKey(function.name)
			preferences[key] = function.definition
		}
	}
}
