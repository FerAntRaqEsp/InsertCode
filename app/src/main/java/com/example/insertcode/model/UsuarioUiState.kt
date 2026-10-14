package com.example.insertcode.model

// Representa el estado global del formulario en la pantalla
data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptarTerminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores() // Sub-objeto para manejar los mensajes de error
)

// Representa los mensajes de error de validación para cada campo
data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null
)

