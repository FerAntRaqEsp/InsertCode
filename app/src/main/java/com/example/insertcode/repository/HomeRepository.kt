package com.example.insertcode.repository

import com.example.insertcode.model.Item

// Repositorio: simula el origen de datos (podría reemplazarse por una API o BD real)
class HomeRepository {

    fun obtenerMensajeBienvenida(): String {
        return "¡Bienvenido!"
    }

    fun obtenerItems(): List<Item> {
        return listOf(
            Item(1, "Elemento 1", "Descripción del elemento 1"),
            Item(2, "Elemento 2", "Descripción del elemento 2")
        )
    }
}