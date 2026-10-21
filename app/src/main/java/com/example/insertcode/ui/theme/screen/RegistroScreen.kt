package com.example.insertcode.ui.theme.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.insertcode.ui.components.ImagenInteligente
import com.example.insertcode.ui.components.PremiumCard
import com.example.insertcode.viewmodel.UsuarioViewModel
import java.io.File

@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val context = LocalContext.current
    // Observamos el estado expuesto por el ViewModel de manera reactiva
    val estado by viewModel.estado.collectAsState()
    val mensajeExito by viewModel.mensajeExito.collectAsState()

    // URI temporal para almacenar de forma segura la foto capturada por la cámara
    var uriFotoCamara by remember { mutableStateOf<Uri?>(null) }

    // Launcher nativo para seleccionar una imagen existente desde la Galería
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) viewModel.actualizarFoto(uri)
    }

    // Launcher nativo para abrir la aplicación de la Cámara del dispositivo
    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exitoso: Boolean ->
        if (exitoso && uriFotoCamara != null) {
            viewModel.actualizarFoto(uriFotoCamara)
        }
    }

    // Función auxiliar requerida en la Parte 5 para generar la URI temporal compartida
    fun crearUriCamara(): Uri {
        val directorio = context.getExternalFilesDir("Pictures")
        val archivo = File.createTempFile("foto_${System.currentTimeMillis()}_", ".jpg", directorio)
        return FileProvider.getUriForFile(
            context,
            "com.example.insertcode.fileprovider",
            archivo
        )
    }

    // 🔹 Parte 2 y 5: Animación del color del botón Premium (Gris -> Verde #2E7D32)
    val colorBotonPremium by animateColorAsState(
        targetValue = if (estado.modoEspecial) Color(0xFF2E7D32) else Color.Gray,
        label = "AnimacionColorPremium"
    )

    // Agregamos scroll vertical en caso de que la pantalla se visualice en dispositivos pequeños
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro Integral",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // 🔹 Parte 4: Componente modular reutilizable que despliega la foto o silueta
        ImagenInteligente(fotoUri = estado.fotoUri)

        // Muestra el error asociado a la falta de foto si se intenta registrar en blanco
        estado.errores.foto?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        // Fila horizontal (Row) con los dos botones nativos multimedia solicitados
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    val uri = crearUriCamara()
                    uriFotoCamara = uri
                    launcherCamara.launch(uri)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cámara")
            }

            Button(
                onClick = { launcherGaleria.launch("image/*") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Galería")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // ========== CAMPO NOMBRE ==========
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text(text = "Nombre") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // ========== CAMPO CORREO ==========
        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            label = { Text(text = "Correo electrónico") },
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // ========== CAMPO CLAVE ==========
        OutlinedTextField(
            value = estado.clave,
            onValueChange = viewModel::onClaveChange,
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // ========== CAMPO DIRECCIÓN ==========
        OutlinedTextField(
            value = estado.direccion,
            onValueChange = viewModel::onDireccionChange,
            label = { Text(text = "Dirección") },
            isError = estado.errores.direccion != null,
            supportingText = {
                estado.errores.direccion?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // ========== CHECKBOX ACEPTAR TÉRMINOS ==========
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = estado.aceptarTerminos,
                onCheckedChange = viewModel::onAceptarTerminosChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Acepto los términos y condiciones")
        }

        // 🔹 Parte 5: Botón Premium con color animado reactivo
        Button(
            onClick = { viewModel.toggleModoEspecial() },
            colors = ButtonDefaults.buttonColors(containerColor = colorBotonPremium),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (estado.modoEspecial) "Desactivar Modo Premium" else "Activar Modo Premium")
        }

        // 🔹 Parte 4: Card Animada con visibilidad dinámica controlada
        PremiumCard(visible = estado.modoEspecial)

        // Feedback animado temporal traído reactivamente desde DataStore
        AnimatedVisibility(visible = mensajeExito != null) {
            mensajeExito?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ========== BOTÓN ENVIAR ACTUALIZADO ==========
        Button(
            onClick = {
                // Si pasa la validación global, guarda en ROOM de forma local y navega a la pantalla "resumen"
                if (viewModel.validarFormulario()) {
                    viewModel.guardarEnRoom() // 🔹 Inserta los datos offline en la BD local
                    navController.navigate("resumen")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar y ver Resumen")
        }
    }
}