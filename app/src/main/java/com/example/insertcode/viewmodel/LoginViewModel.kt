package com.example.insertcode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insertcode.model.LoginErrores
import com.example.insertcode.model.LoginUiState
import com.example.insertcode.navigation.NavigationEvent
import com.example.insertcode.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // 1. Estado de la UI utilizando LoginUiState
    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    // 2. Canal de eventos de navegación reactiva requerido por la evaluación
    private val _eventosNavegacion = MutableSharedFlow<NavigationEvent>()
    val eventosNavegacion: SharedFlow<NavigationEvent> = _eventosNavegacion.asSharedFlow()

    // Actualiza el campo de correo electrónico y limpia su error
    fun onEmailChange(valor: String) {
        _estado.update {
            it.copy(email = valor, errores = it.errores.copy(email = null))
        }
    }

    // Actualiza el campo de contraseña y limpia su error
    fun onPasswordChange(valor: String) {
        _estado.update {
            it.copy(password = valor, errores = it.errores.copy(password = null))
        }
    }

    // Funciones de navegación requeridas por el enunciado
    fun navigateTo(route: String) {
        viewModelScope.launch {
            _eventosNavegacion.emit(NavigationEvent.NavigateTo(route))
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _eventosNavegacion.emit(NavigationEvent.NavigateBack)
        }
    }

    // 3. Validación y Autenticación con los 3 usuarios del Modo Guardián
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value

        // Validaciones de formato iniciales
        val emailError = if (estadoActual.email.isBlank()) {
            "El correo es obligatorio"
        } else if (!estadoActual.email.contains("@")) {
            "Formato de correo inválido"
        } else null

        val passwordError = if (estadoActual.password.isBlank()) {
            "La contraseña es obligatoria"
        } else if (estadoActual.password.length < 6) {
            "Debe tener al menos 6 caracteres"
        } else null

        if (emailError != null || passwordError != null) {
            _estado.update { it.copy(errores = LoginErrores(email = emailError, password = passwordError)) }
            return false
        }

        // Simulación de carga (isLoading) ante errores de datos o conectividad
        _estado.update { it.copy(isLoading = true) }

        // Control y validación estricta de credenciales ficticias obligatorias
        val emailInput = estadoActual.email.trim()
        val passwordInput = estadoActual.password

        when {
            emailInput == "admin@guardian.test" && passwordInput == "123456" -> {
                _estado.update { it.copy(isLoading = false, rol = "Admin") }
                navigateTo(Screen.HomeAdmin.route)
            }
            emailInput == "supervisor@guardian.test" && passwordInput == "123456" -> {
                _estado.update { it.copy(isLoading = false, rol = "Supervisor") }
                navigateTo(Screen.HomeSupervisor.route)
            }
            emailInput == "operador@guardian.test" && passwordInput == "123456" -> {
                _estado.update { it.copy(isLoading = false, rol = "Operador") }
                navigateTo(Screen.HomeOperator.route)
            }
            else -> {
                _estado.update {
                    it.copy(
                        isLoading = false,
                        errores = LoginErrores(
                            email = "Credenciales incorrectas o usuario no registrado",
                            password = "Verifique su contraseña"
                        )
                    )
                }
                return false
            }
        }
        return true
    }


    fun irAPantallaEspecial() {
        viewModelScope.launch {
            _eventosNavegacion.emit(NavigationEvent.NavigateTo(Screen.AnimacionesEstados.route))
        }
    }
}


