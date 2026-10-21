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
import com.example.insertcode.ui.theme.screen.RegistroScreen
import com.example.insertcode.ui.theme.screen.ResumenScreen
import com.example.insertcode.viewmodel.EstadoViewModel
import com.example.insertcode.viewmodel.LoginViewModel
import com.example.insertcode.viewmodel.PerfilViewModel
import com.example.insertcode.viewmodel.UsuarioViewModel

// 1. Definición de las pantallas del sistema (Sealed Class)
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object HomeAdmin : Screen("home_admin")
    data object HomeSupervisor : Screen("home_supervisor")
    data object HomeOperator : Screen("home_operator")
    data object AnimacionesEstados : Screen("animaciones_estados")
    data object Perfil : Screen("perfil")
    // 🔹 Rutas obligatorias para la Guía 14 (Módulo Integral Room)
    data object RegistroIntegral : Screen("registro")
    data object ResumenIntegral : Screen("resumen")
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
    usuarioViewModel: UsuarioViewModel, // 🔹 ViewModel inyectado desde la base de datos central
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = loginViewModel)
        }
        composable(route = Screen.HomeAdmin.route) {
            HomeAdminScreen(viewModel = loginViewModel, navController = navController)
        }
        composable(route = Screen.HomeSupervisor.route) {
            HomeSupervisorScreen(viewModel = loginViewModel, navController = navController)
        }
        composable(route = Screen.HomeOperator.route) {
            HomeOperatorScreen(viewModel = loginViewModel, navController = navController)
        }
        composable(route = Screen.AnimacionesEstados.route) {
            PantallaPrincipal(viewModel = estadoViewModel)
        }
        composable(route = Screen.Perfil.route) {
            PerfilScreen(viewModel = perfilViewModel)
        }

        composable(route = Screen.RegistroIntegral.route) {
            RegistroScreen(navController = navController, viewModel = usuarioViewModel)
        }
        composable(route = Screen.ResumenIntegral.route) {
            ResumenScreen(navController = navController, viewModel = usuarioViewModel)
        }
    }
}