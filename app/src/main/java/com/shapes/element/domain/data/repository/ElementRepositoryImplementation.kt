package com.shapes.element.domain.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shapes.element.domain.model.Element
import com.shapes.element.domain.repository.ElementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ElementRepositoryImplementation : ElementRepository {
	companion object {
		private val Context.dataStore by preferencesDataStore("ElementApplicationInformation")
		private val key = stringPreferencesKey("ElementList")
	}

	override fun getElementList(context: Context): Flow<List<Element>> {
		return context.dataStore.data.map { preferences ->
			val dataString = preferences[key]
			if (!dataString.isNullOrEmpty()) {
				Json.decodeFromString(dataString)
			} else {
				emptyList()
			}
		}
	}

	override suspend fun setElementList(context: Context, data: List<Element>) {
		context.dataStore.edit { preferences ->
			preferences[key] = Json.encodeToString(data)
		}
	}
}
