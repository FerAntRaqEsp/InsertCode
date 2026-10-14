package com.example.insertcode.ui.theme.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.insertcode.ui.ImagenInteligente // ✅ CORREGIDO: Ruta real sin la palabra 'components'
import com.example.insertcode.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel,
    modifier: Modifier = Modifier
) {
    // Escuchamos el estado reactivo del URI de la imagen desde el ViewModel
    val imagenUri by viewModel.imagenUri.collectAsState()

    // 1. Lanzador nativo para abrir la Galería del dispositivo
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.actualizarImagen(uri)
    }

    // 2. Lanzador nativo para activar la Cámara y capturar una foto nueva
    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito) {
            viewModel.confirmarFotoCamara()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Perfil de Usuario",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Renderizado del componente circular inteligente (muestra foto o ícono por defecto)
        ImagenInteligente(imagenUri = imagenUri)

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para la Galería
        Button(
            onClick = { launcherGaleria.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar desde Galería")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón para la Cámara
        Button(
            onClick = {
                // Generamos un URI seguro temporal antes de lanzar la cámara
                val uriSeguro = viewModel.crearUriTemporal()
                launcherCamara.launch(uriSeguro)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar Foto con Cámara")
        }
    }
}
