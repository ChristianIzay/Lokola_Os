package com.muana.lokola.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.muana.lokola.util.ThemeMode
import com.muana.lokola.util.ThemeModeManager

@Composable
fun LokolaOSTheme(
    themeModeManager: ThemeModeManager,
    dynamicColor: Boolean = false, // Disable dynamic colors for cultural theme consistency
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    
    // Récupérer le mode de thème choisi par l'utilisateur
    val themeMode by themeModeManager.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
    
    // Déterminer si on utilise le mode sombre
    val isDarkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDarkTheme -> DarkColorScheme  // Utilise le schéma sombre complet défini dans Color.kt
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Mode sombre : status bar transparente avec icônes claires
            // Mode clair : status bar avec couleur primaire et icônes sombres
            if (isDarkTheme) {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            } else {
                window.statusBarColor = colorScheme.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
