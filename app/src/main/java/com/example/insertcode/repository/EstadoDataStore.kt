package com.example.insertcode.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Declaración del DataStore
val Context.dataStore by preferencesDataStore(name = "preferences_usuario")

class EstadoDataStore(private val context: Context) {

    // Nombre corregido según sugerencia de Android Studio (CamelCase sin guiones bajos)
    private val estadoActivado = booleanPreferencesKey("modo_activado")

    // Función para guardar el estado en DataStore
    suspend fun guardarEstado(valor: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[estadoActivado] = valor
        }
    }

    // Función corregida con manejo de excepciones para evitar el error de inferencia de tipo
    fun obtenerEstado(): Flow<Boolean> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[estadoActivado] ?: false
            }
    }
}
