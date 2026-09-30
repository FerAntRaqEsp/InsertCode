package com.example.insertcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.insertcode.navigation.AppNavigation
import com.example.insertcode.ui.theme.InsertCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Mantenemos tu tema original del proyecto
            InsertCodeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Contenedor que aplica los márgenes seguros de la pantalla (Edge-to-Edge)
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // Aquí iniciamos tu flujo de pantallas en lugar del "Greeting" por defecto
                        AppNavigation()
                    }
                }
            }
        }
    }
}
