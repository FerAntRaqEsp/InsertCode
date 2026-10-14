package com.example.insertcode.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.insertcode.ui.theme.screen.HomeAdminScreen
import com.example.insertcode.ui.theme.screen.HomeOperatorScreen
import com.example.insertcode.ui.theme.screen.HomeSupervisorScreen
import com.example.insertcode.ui.theme.screen.LoginScreen
import com.example.insertcode.ui.theme.screen.PantallaPrincipal
import com.example.insertcode.ui.theme.screen.PerfilScreen
import com.example.insertcode.viewmodel.EstadoViewModel
import com.example.insertcode.viewmodel.LoginViewModel
import com.example.insertcode.viewmodel.PerfilViewModel

// 1. Definición de las pantallas del sistema (Sealed Class)
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object HomeAdmin : Screen("home_admin")
    data object HomeSupervisor : Screen("home_supervisor")
    data object HomeOperator : Screen("home_operator")
    data object AnimacionesEstados : Screen("animaciones_estados")
    data object Perfil : Screen("perfil")
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
    estadoViewModel: EstadoViewModel,
    perfilViewModel: PerfilViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        // Pantalla de Login Principal
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = loginViewModel)
        }

        // Pantalla de Inicio para el Rol Administrador
        composable(route = Screen.HomeAdmin.route) {
            HomeAdminScreen(viewModel = loginViewModel, navController = navController)
        }

        // Pantalla de Inicio para el Rol Supervisor
        composable(route = Screen.HomeSupervisor.route) {
            HomeSupervisorScreen(viewModel = loginViewModel, navController = navController)
        }

        // Pantalla de Inicio para el Rol Operador
        composable(route = Screen.HomeOperator.route) {
            HomeOperatorScreen(viewModel = loginViewModel, navController = navController)
        }

        // Pantalla asociada a la Guía 12 (Animaciones y Estados)
        composable(route = Screen.AnimacionesEstados.route) {
            PantallaPrincipal(viewModel = estadoViewModel)
        }

        // Pantalla asociada a la Guía 13 (Cámara y Galería)
        composable(route = Screen.Perfil.route) {
            PerfilScreen(viewModel = perfilViewModel)
        }
    }
}
