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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.insertcode.navigation.AppNavigation
import com.example.insertcode.navigation.NavigationEvent
import com.example.insertcode.repository.AppDatabase
import com.example.insertcode.repository.HomeRepository
import com.example.insertcode.repository.PreferencesManager
import com.example.insertcode.ui.theme.InsertCodeTheme
import com.example.insertcode.viewmodel.LoginViewModel
import com.example.insertcode.viewmodel.EstadoViewModel
import com.example.insertcode.viewmodel.PerfilViewModel
import com.example.insertcode.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val estadoViewModel: EstadoViewModel by viewModels()
    private val perfilViewModel: PerfilViewModel by viewModels()

    // 🔹 Inicialización de la factoría personalizada para inyectar Room + DataStore en el ViewModel
    private val usuarioViewModel: UsuarioViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // 1. Instanciamos la base de datos de ROOM Singleton (Parte 1)
                val database = AppDatabase.getDatabase(applicationContext)

                // 2. Instanciamos el administrador de preferencias de DataStore (Parte 2)
                val preferencesManager = PreferencesManager(applicationContext)

                // 3. Acoplamos ambos en el repositorio centralizado
                val repository = HomeRepository(database.usuarioDao(), preferencesManager)

                @Suppress("UNCHECKED_CAST")
                return UsuarioViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InsertCodeTheme {
                val navController = rememberNavController()

                // Restablecemos el LaunchedEffect reactivo requerido por la evaluación
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
                            perfilViewModel = perfilViewModel,
                            usuarioViewModel = usuarioViewModel // 🔹 Pasamos el ViewModel de la Guía 14 listo
                        )
                    }
                }
            }
        }
    }
}