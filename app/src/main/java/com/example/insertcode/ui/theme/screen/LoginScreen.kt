package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.insertcode.model.LoginUiState
import com.example.insertcode.ui.utils.WindowSizeClass
import com.example.insertcode.ui.utils.rememberWindowSizeClass
import com.example.insertcode.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val windowSize = rememberWindowSizeClass()
    val estado by viewModel.estado.collectAsState()


    val isLoginEnabled = estado.email.isNotBlank() && estado.password.isNotBlank() && !estado.isLoading


    if (windowSize == WindowSizeClass.COMPACT) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginFormContent(estado = estado, viewModel = viewModel, isLoginEnabled = isLoginEnabled)
        }
    } else {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.widthIn(max = 450.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginFormContent(estado = estado, viewModel = viewModel, isLoginEnabled = isLoginEnabled)
                }
            }
        }
    }
}

// Interfaz interna reutilizable para ambos modos de pantalla
@Composable
fun LoginFormContent(
    estado: LoginUiState,
    viewModel: LoginViewModel,
    isLoginEnabled: Boolean
) {
    Text(
        text = "Caso Modo Guardián",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(bottom = 24.dp)
    )

    // ========== CAMPO EMAIL ==========
    OutlinedTextField(
        value = estado.email,
        onValueChange = viewModel::onEmailChange,
        label = { Text("Correo electrónico") },
        isError = estado.errores.email != null,
        supportingText = {
            estado.errores.email?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // ========== CAMPO CONTRASEÑA ==========
    OutlinedTextField(
        value = estado.password,
        onValueChange = viewModel::onPasswordChange,
        label = { Text("Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        isError = estado.errores.password != null,
        supportingText = {
            estado.errores.password?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(24.dp))

    // Control de estado de carga requerido para simular conectividad
    if (estado.isLoading) {
        CircularProgressIndicator()
    } else {
        // BOTÓN ENVIAR
        Button(
            onClick = { viewModel.validarFormulario() },
            enabled = isLoginEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
    }
}

