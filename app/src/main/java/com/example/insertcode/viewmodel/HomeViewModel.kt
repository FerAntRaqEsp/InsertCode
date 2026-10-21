package com.example.insertcode.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.insertcode.repository.HomeRepository

// ViewModel asociado a HomeScreen: expone el estado de la UI y la lógica de presentación
class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    // 🔹 Solución: Usamos la asignación directa con '.value' en lugar de 'by' para evitar el fallo del compilador
    val _mensajeBienvenida = mutableStateOf(repository.obtenerMensajeBienvenida())

    // Propiedad calculada para mantener la compatibilidad con tu pantalla HomeScreen original
    var mensajeBienvenida: String
        get() = _mensajeBienvenida.value
        private set(value) { _mensajeBienvenida.value = value }

    fun onBotonPresionado() {
        // Acción de ejemplo al presionar el botón: actualiza el mensaje de bienvenida de forma segura
        mensajeBienvenida = "¡Gracias por presionar el botón!"
    }
}
