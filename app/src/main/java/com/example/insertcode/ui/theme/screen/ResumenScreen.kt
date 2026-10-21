package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.insertcode.ui.components.ImagenInteligente
import com.example.insertcode.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    navController: NavController, // 🔹 Añadido: Permite controlar los flujos de navegación de la app
    viewModel: UsuarioViewModel
) {
    // Observamos el estado expuesto por el mismo ViewModel de manera reactiva
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Resumen del Registro",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // 🔹 Añadido: Muestra la foto de perfil en formato circular (Parte 6 de la guía)
        ImagenInteligente(fotoUri = estado.fotoUri)

        Spacer(modifier = Modifier.height(8.dp))

        // Contenedor tipo Card para agrupar estéticamente tus datos originales
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Nombre: ${estado.nombre}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Correo: ${estado.correo}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Dirección: ${estado.direccion}", style = MaterialTheme.typography.bodyLarge)

                // Mantiene tu lógica original para enmascarar la contraseña
                Text(text = "Contraseña: ${"*".repeat(estado.clave.length)}", style = MaterialTheme.typography.bodyLarge)

                // Mantiene tu lógica original de los términos
                Text(text = "Términos: ${if (estado.aceptarTerminos) "Aceptados ✅" else "No aceptados ❌"}", style = MaterialTheme.typography.bodyLarge)

                // 🔹 Añadido: Muestra de forma reactiva si el modo es Premium o Básico
                Text(
                    text = "Tipo de Cuenta: ${if (estado.modoEspecial) "Premium ⭐" else "Básica"}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para regresar al formulario limpiando el historial de navegación anterior
        Button(
            onClick = {
                navController.navigate("registro") {
                    popUpTo("registro") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Formulario")
        }
    }
}