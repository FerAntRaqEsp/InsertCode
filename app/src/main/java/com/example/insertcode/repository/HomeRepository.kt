package com.example.insertcode.repository

import com.example.insertcode.model.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class HomeRepository(
    private val usuarioDao: com.example.insertcode.repository.UsuarioDao,
    private val preferencesManager: PreferencesManager
) {
    // 🔹 MÉTODOS DE LA GUÍA 14 (ROOM FLOW)
    val todosLosUsuarios: Flow<List<UsuarioEntity>> = usuarioDao.getUsuariosFlow()

    suspend fun insertarUsuario(usuario: UsuarioEntity) {
        usuarioDao.insertar(usuario)
    }

    suspend fun eliminarUsuario(usuario: UsuarioEntity) {
        usuarioDao.eliminar(usuario)
    }

    fun buscarUsuarios(correo: String): Flow<List<UsuarioEntity>> {
        return usuarioDao.buscarPorCorreo(correo)
    }

    // 🔹 MÉTODOS DE LA GUÍA 14 (DATASTORE FLOW)
    val modoEspecial: Flow<Boolean> = preferencesManager.modoEspecialFlow

    suspend fun actualizarModoPremium(activado: Boolean) {
        preferencesManager.guardarModoEspecial(activado)
    }

    // 🔹 SOLUCIÓN AL ERROR ACTUAL: Función requerida por tu HomeViewModel base
    fun obtenerMensajeBienvenida(): String {
        return "¡Bienvenido a la Aplicación Base!"
    }
}