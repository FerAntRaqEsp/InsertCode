package com.example.insertcode.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

// Definición de las clases de tamaño requeridas por la rúbrica
enum class WindowSizeClass {
    COMPACT,
    MEDIUM,
    EXPANDED
}

/**
 * Calcula dinámicamente el tamaño de la ventana basándose en la configuración actual
 * del dispositivo (detecta rotaciones y cambios de pantalla en tiempo real).
 */
@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val configuration = LocalConfiguration.current
    val widthDp = configuration.screenWidthDp

    return when {
        widthDp < 600 -> WindowSizeClass.COMPACT      // Celulares en posición vertical (Portrait)
        widthDp < 840 -> WindowSizeClass.MEDIUM       // Celulares en horizontal (Landscape) o Tablets chicas
        else -> WindowSizeClass.EXPANDED              // Tablets grandes o Pantallas Expandidas
    }
}
