package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.navigation.Screen
import com.example.insertcode.viewmodel.LoginViewModel

@Composable
fun HomeAdminScreen(viewModel: LoginViewModel, navController: NavHostController) {
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Panel de Administración", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Usuario autenticado: ${estado.email}")
        Text("Rol asignado: ${estado.rol}")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.irAPantallaEspecial() }) {
            Text("Ir a Modo Especial (Guía 12)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🚀 Botón para abrir la Cámara y Galería (Guía 13)
        Button(onClick = { navController.navigate(Screen.Perfil.route) }) {
            Text("Ir a Mi Perfil (Cámara/Galería)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { viewModel.navigateBack() }) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun HomeSupervisorScreen(viewModel: LoginViewModel, navController: NavHostController) {
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Módulo de Supervisión", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Usuario autenticado: ${estado.email}")
        Text("Rol asignado: ${estado.rol}")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.irAPantallaEspecial() }) {
            Text("Ir a Modo Especial (Guía 12)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🚀 Botón para Supervisor
        Button(onClick = { navController.navigate(Screen.Perfil.route) }) {
            Text("Ir a Mi Perfil (Cámara/Galería)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { viewModel.navigateBack() }) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun HomeOperatorScreen(viewModel: LoginViewModel, navController: NavHostController) {
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Consola de Operador", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Usuario autenticado: ${estado.email}")
        Text("Rol asignado: ${estado.rol}")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.irAPantallaEspecial() }) {
            Text("Ir a Modo Especial (Guía 12)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🚀 Botón para Operador
        Button(onClick = { navController.navigate(Screen.Perfil.route) }) {
            Text("Ir a Mi Perfil (Cámara/Galería)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { viewModel.navigateBack() }) {
            Text("Cerrar Sesión")
        }
    }
}

