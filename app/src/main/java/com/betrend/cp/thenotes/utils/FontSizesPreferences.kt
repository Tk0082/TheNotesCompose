package com.betrend.cp.thenotes.utils

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "font_size_preferences")

object FontSizePreferences {
    private val FONT_SIZE_KEY = intPreferencesKey("font_size")


    fun getFontSize(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[FONT_SIZE_KEY] ?: 18 // Valor padrão
        }
    }

//    fun setFontSize(context: CoroutineScope, fontSize: Int) {
//        context.dataStore.edit { preferences ->
//            preferences[FONT_SIZE_KEY] = fontSize
//        }
//    }
}
