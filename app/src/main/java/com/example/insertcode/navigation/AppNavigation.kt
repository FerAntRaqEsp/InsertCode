package com.example.insertcode.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.insertcode.ui.theme.screen.LoginScreen
import com.example.insertcode.ui.theme.screen.PantallaPrincipal // 1. IMPORTANTE: Importar la nueva pantalla
import com.example.insertcode.viewmodel.LoginViewModel
import com.example.insertcode.viewmodel.EstadoViewModel // 2. IMPORTANTE: Importar el nuevo ViewModel

// 1. Definición de las pantallas del sistema (Sealed Class oficial)
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object HomeAdmin : Screen("home_admin")
    data object HomeSupervisor : Screen("home_supervisor")
    data object HomeOperator : Screen("home_operator")
    data object AnimacionesEstados : Screen("animaciones_estados") // 3. AGREGADO: Nueva ruta en la Sealed Class
}

// 2. Eventos de navegación reactiva despachados por el ViewModel
sealed class NavigationEvent {
    data class NavigateTo(val route: String) : NavigationEvent()
    data object NavigateBack : NavigationEvent()
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    estadoViewModel: EstadoViewModel // 4. AGREGADO: Recibimos el parámetro para solucionar el error de MainActivity
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route // Inicia directamente en el Login
    ) {
        // Pantalla de Login Principal
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = loginViewModel)
        }

        // Pantalla de Inicio para el Rol Administrador
        composable(route = Screen.HomeAdmin.route) {
            com.example.insertcode.ui.theme.screen.HomeAdminScreen(loginViewModel)
        }

        // Pantalla de Inicio para el Rol Supervisor
        composable(route = Screen.HomeSupervisor.route) {
            com.example.insertcode.ui.theme.screen.HomeSupervisorScreen(loginViewModel)
        }

        // Pantalla de Inicio para el Rol Operador
        composable(route = Screen.HomeOperator.route) {
            com.example.insertcode.ui.theme.screen.HomeOperatorScreen(loginViewModel)
        }

        // 5. AGREGADO: Nueva pantalla asociada a la ruta de la guía
        composable(route = Screen.AnimacionesEstados.route) {
            PantallaPrincipal(viewModel = estadoViewModel)
        }
    }
}

