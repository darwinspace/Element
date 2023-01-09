package com.space.element.domain.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.space.element.domain.model.Element
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ElementDatabaseImplementation @Inject constructor(
	private val dataStore: DataStore<Preferences>
) : ElementDatabase {
	override fun getList(): Flow<List<Element>> {
		return dataStore.data.map { preferences ->
			preferences.asMap().map {
				val name = it.key.name
				val value = it.value.toString()
				Element(name, value)
			}
		}
	}

	private suspend fun setElementInList(element: Element) {
		dataStore.edit { preferences ->
			val key = stringPreferencesKey(element.name)
			preferences[key] = element.value
		}
	}

	override suspend fun addElementToList(element: Element) {
		setElementInList(element)
	}

	override suspend fun editElementFromList(element: Element) {
		setElementInList(element)
	}

	override suspend fun removeElementFromList(element: Element) {
		dataStore.edit { preferences ->
			val key = stringPreferencesKey(element.name)
			preferences.remove(key)
		}
	}
}
