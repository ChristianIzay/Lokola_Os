package com.muana.lokola.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.muana.lokola.ui.navigation.LokolaNavHost
import com.muana.lokola.ui.theme.LokolaOSTheme
import com.muana.lokola.util.ThemeModeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var themeModeManager: ThemeModeManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Le Splash Screen ne s'affiche qu'au premier lancement de l'activité principale
        // Il ne doit PAS s'afficher lors des retours en arrière dans la navigation
        setContent {
            LokolaOSTheme(themeModeManager = themeModeManager) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    LokolaNavHost(navController = navController)
                }
            }
        }
    }
}
