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
import com.example.insertcode.viewmodel.EstadoViewModel // 1. IMPORTA EL NUEVO VIEWMODEL

class MainActivity : ComponentActivity() {

    // Instanciamos el LoginViewModel en la actividad principal para compartirlo
    private val loginViewModel: LoginViewModel by viewModels()

    // 2. INSTANCIAMOS EL NUEVO VIEWMODEL (Necesita inicializarse aquí porque usa AndroidViewModel)
    private val estadoViewModel: EstadoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InsertCodeTheme {
                val navController = rememberNavController()

                // LaunchedEffect que colecta los eventos de navegación reactiva del ViewModel
                LaunchedEffect(key1 = true) {
                    loginViewModel.eventosNavegacion.collect { evento ->
                        when (evento) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(evento.route) {
                                    // Evita acumular pantallas repetidas en la pila
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
                        // 3. PASAMOS EL NUEVO VIEWMODEL HACIA APPNAVIGATION
                        AppNavigation(
                            navController = navController,
                            loginViewModel = loginViewModel,
                            estadoViewModel = estadoViewModel // Agregado aquí
                        )
                    }
                }
            }
        }
    }
}

