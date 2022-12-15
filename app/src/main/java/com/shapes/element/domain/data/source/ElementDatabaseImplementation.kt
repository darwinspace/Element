package com.shapes.element.domain.data.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shapes.element.domain.model.Element
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ElementDatabaseImplementation : ElementDatabase {
	companion object {
		private val Context.dataStore by preferencesDataStore("ApplicationElementList")
	}

	override fun getList(context: Context): Flow<List<Element>> {
		return context.dataStore.data.map { preferences ->
			preferences.asMap().map {
				val name = it.key.name
				val value = it.value.toString()
				Element(name, value)
			}
		}
	}

	private suspend fun setElementInList(context: Context, element: Element) {
		context.dataStore.edit { preferences ->
			val key = stringPreferencesKey(element.name)
			preferences[key] = element.value
		}
	}

	override suspend fun addElementToList(context: Context, element: Element) {
		setElementInList(context, element)
	}

	override suspend fun editElementFromList(context: Context, element: Element) {
		setElementInList(context, element)
	}

	override suspend fun removeElementFromList(context: Context, element: Element) {
		context.dataStore.edit {preferences->
			val key = stringPreferencesKey(element.name)
			preferences.remove(key)
		}
	}
}
