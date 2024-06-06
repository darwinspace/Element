package com.space.element.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.space.element.domain.data.ElementDatabase
import com.space.element.domain.model.Element
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ElementDatabaseImplementation @Inject constructor(
	private val dataStore: DataStore<Preferences>
) : ElementDatabase {
	override fun getList(): Flow<List<Element>> {
		return dataStore.data.map { preferences ->
			preferences.asMap().map { (key, value) ->
				Element(key.name, value.toString())
			}.reversed()
		}
	}

	override suspend fun add(element: Element) {
		dataStore.edit { preferences ->
			val key = stringPreferencesKey(element.name)
			preferences[key] = element.value
		}
	}

	override suspend fun remove(element: Element) {
		dataStore.edit { preferences ->
			val key = stringPreferencesKey(element.name)
			preferences.remove(key)
		}
	}
}
