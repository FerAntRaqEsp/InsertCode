package com.example.insertcode.viewmodel

import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    // Variable temporal para guardar la foto que tomará la cámara
    private var uriTemporalCamara: Uri? = null

    fun actualizarImagen(uri: Uri?) {
        if (uri != null) {
            _imagenUri.value = uri
        }
    }

    // Genera un URI seguro usando el FileProvider configurado para capturar la foto
    fun crearUriTemporal(): Uri {
        val contexto = getApplication<Application>().applicationContext
        val directorioCache = contexto.cacheDir
        val archivoTemporal = File.createTempFile("foto_perfil_", ".jpg", directorioCache)

        val uri = FileProvider.getUriForFile(
            contexto,
            "com.example.insertcode.fileprovider",
            archivoTemporal
        )
        uriTemporalCamara = uri
        return uri
    }

    fun confirmarFotoCamara() {
        _imagenUri.value = uriTemporalCamara
    }
}
