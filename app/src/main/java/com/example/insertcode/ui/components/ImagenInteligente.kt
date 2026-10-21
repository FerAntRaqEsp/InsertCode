package com.example.insertcode.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImagenInteligente(
    fotoUri: Uri?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (fotoUri != null) {
            // Carga de la foto utilizando Coil de manera reactiva
            AsyncImage(
                model = fotoUri,
                contentDescription = "Foto de perfil del usuario",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            // Icono por defecto en caso de que no exista foto aún
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Sin foto de perfil",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
