package com.example.insertcode.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Cambiamos el nombre de la extensión a 'userPrefsDataStore' para solucionar las "Conflicting declarations"
private val Context.userPrefsDataStore by preferencesDataStore(name = "user_preferences")

class PreferencesManager(private val context: Context) {

    // Cambiado a camelCase sin guiones bajos para quitar el Warning de nomenclatura de Kotlin
    private val modoEspecialKey = booleanPreferencesKey("modo_especial")

    // Expone el flujo reactivo del estado premium
    val modoEspecialFlow: Flow<Boolean> = context.userPrefsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[modoEspecialKey] ?: false
        }

    // Función de escritura asíncrona
    suspend fun guardarModoEspecial(esPremium: Boolean) {
        context.userPrefsDataStore.edit { preferences ->
            preferences[modoEspecialKey] = esPremium
        }
    }
}
