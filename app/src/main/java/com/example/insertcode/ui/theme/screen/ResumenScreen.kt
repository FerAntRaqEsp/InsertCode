package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.insertcode.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(viewModel: UsuarioViewModel) {
    // Observamos el estado expuesto por el mismo ViewModel de manera reactiva
    val estado by viewModel.estado.collectAsState()

    Column(modifier = Modifier.padding(all = 16.dp)) {
        Text(
            text = "Resumen del Registro",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(text = "Nombre: ${estado.nombre}")
        Text(text = "Correo: ${estado.correo}")
        Text(text = "Dirección: ${estado.direccion}")

        // Muestra la contraseña enmascarada con asteriscos según su longitud
        Text(text = "Contraseña: ${"*".repeat(estado.clave.length)}")

        // Muestra si aceptó o no los términos condicionalmente
        Text(text = "Términos: ${if (estado.aceptarTerminos) "Aceptados" else "No aceptados"}")
    }
}
