package com.example.insertcode.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insertcode.model.UsuarioEntity
import com.example.insertcode.model.UsuarioErrores
import com.example.insertcode.model.UsuarioUiState
import com.example.insertcode.repository.HomeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

// Añadimos el constructor para inyectar nuestro repositorio de datos
class UsuarioViewModel(private val repository: HomeRepository) : ViewModel() {

    // Mantienes tu Estado interno mutable original
    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado.asStateFlow()

    // 🔹 Estados adicionales requeridos por las Partes 2 y 3
    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _mensajeExito = MutableStateFlow<String?>(null)
    val mensajeExito: StateFlow<String?> = _mensajeExito.asStateFlow()

    init {
        // Simulación de carga inicial de 1500ms al iniciar la aplicación
        viewModelScope.launch {
            delay(1500)
            _cargando.value = false
        }

        // Sincronización automática con las preferencias de DataStore
        viewModelScope.launch {
            repository.modoEspecial.collect { premiumActivo ->
                _estado.update { it.copy(modoEspecial = premiumActivo) }
            }
        }
    }

    // Actualiza el campo nombre y limpia su error
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    // Actualiza el campo correo y limpia su error
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    // Actualiza el campo clave y limpia su error
    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    // Actualiza el campo direccion y limpia su error
    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    // Actualiza checkbox de aceptación (Sincronizado con la guía)
    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptarTerminos = valor) }
    }

    // 🔹 Añadido: Actualiza la foto y limpia el error de foto
    fun actualizarFoto(uri: Uri?) {
        _estado.update { it.copy(fotoUri = uri, errores = it.errores.copy(foto = null)) }
    }

    // 🔹 Añadido: Lógica de activación del Modo Premium (DataStore + Mensaje temporal)
    fun toggleModoEspecial() {
        viewModelScope.launch {
            val nuevoValor = !_estado.value.modoEspecial
            repository.actualizarModoPremium(nuevoValor)

            if (nuevoValor) {
                _mensajeExito.value = "¡Beneficios Premium activados!"
                delay(2000) // Desaparece tras 2 segundos
                _mensajeExito.value = null
            }
        }
    }

    // Validación global del formulario adaptada a los requisitos de la Guía 14
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value

        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.length < 3) "El nombre debe tener al menos 3 caracteres" else null,
            correo = if (!estadoActual.correo.contains("@")) "El correo debe incluir un '@'" else null,
            clave = if (estadoActual.clave.length < 6) "La contraseña debe tener al menos 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "La dirección no puede estar vacía" else null,
            foto = if (estadoActual.fotoUri == null) "Debes seleccionar o capturar una foto de perfil" else null
        )

        // Verificamos si existe algún mensaje de error dentro del objeto
        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion,
            errores.foto
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        // Es válido si no hay errores en los campos y aceptó términos
        return !hayErrores && estadoActual.aceptarTerminos
    }

    // 🔹 Añadido: Guardar los datos finales de la UI dentro de la BD local ROOM
    fun guardarEnRoom() {
        viewModelScope.launch {
            val estadoActual = _estado.value

            val usuarioEntidad = UsuarioEntity(
                nombre = estadoActual.nombre,
                correo = estadoActual.correo,
                direccion = estadoActual.direccion,
                modoEspecial = estadoActual.modoEspecial,
                fotoPath = estadoActual.fotoUri?.toString(),
                fechaRegistro = Date()
            )

            repository.insertarUsuario(usuarioEntidad)
        }
    }
}