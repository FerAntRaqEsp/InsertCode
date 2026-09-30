package com.example.insertcode.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.insertcode.repository.HomeRepository

// ViewModel asociado a HomeScreen: expone el estado de la UI y la lógica de presentación
class HomeViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    // Estado observable que la pantalla (Composable) puede leer y recomponer
    var mensajeBienvenida by mutableStateOf(repository.obtenerMensajeBienvenida())
        private set

    fun onBotonPresionado() {
        // Acción de ejemplo al presionar el botón: actualiza el mensaje de bienvenida
        mensajeBienvenida = "¡Gracias por presionar el botón!"
    }
}