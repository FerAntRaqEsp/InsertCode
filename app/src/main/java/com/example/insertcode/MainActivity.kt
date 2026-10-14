package com.example.insertcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.insertcode.navigation.AppNavigation
import com.example.insertcode.navigation.NavigationEvent
import com.example.insertcode.ui.theme.InsertCodeTheme
import com.example.insertcode.viewmodel.LoginViewModel
import com.example.insertcode.viewmodel.EstadoViewModel
import com.example.insertcode.viewmodel.PerfilViewModel // 🚀 Importación forzada explícita

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val estadoViewModel: EstadoViewModel by viewModels()
    private val perfilViewModel: PerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InsertCodeTheme {
                val navController = rememberNavController()

                // 🚀 Restablecemos el LaunchedEffect reactivo requerido por la evaluación
                LaunchedEffect(key1 = true) {
                    loginViewModel.eventosNavegacion.collect { evento ->
                        when (evento) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(evento.route) {
                                    launchSingleTop = true
                                }
                            }
                            is NavigationEvent.NavigateBack -> {
                                navController.popBackStack()
                            }
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        AppNavigation(
                            navController = navController,
                            loginViewModel = loginViewModel,
                            estadoViewModel = estadoViewModel,
                            perfilViewModel = perfilViewModel
                        )
                    }
                }
            }
        }
    }
}
