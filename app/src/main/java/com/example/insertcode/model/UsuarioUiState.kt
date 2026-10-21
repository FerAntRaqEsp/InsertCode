package com.example.insertcode.model

import android.net.Uri

// Representa el estado global del formulario en la pantalla
data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptarTerminos: Boolean = false,
    val fotoUri: Uri? = null,          // 🔹 Añadido: Para capturar la imagen de cámara o galería
    val modoEspecial: Boolean = false, // 🔹 Añadido: Para persistir si es Premium o Básico
    val errores: UsuarioErrores = UsuarioErrores() // Sub-objeto para manejar los mensajes de error
)

// Representa los mensajes de error de validación para cada campo
data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null,
    val foto: String? = null          // 🔹 Añadido: La guía exige que la foto no sea null al validar
)

