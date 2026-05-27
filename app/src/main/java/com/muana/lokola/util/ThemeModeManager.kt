package com.muana.lokola.util

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.themeModeDataStore by preferencesDataStore(name = "theme_mode_prefs")

/**
 * Mode de thème sélectionné par l'utilisateur
 */
enum class ThemeMode {
    LIGHT,      // Toujours mode clair
    DARK,       // Toujours mode sombre
    SYSTEM      // Suit le paramètre système
}

/**
 * Gestionnaire du mode de thème
 * Permet à l'utilisateur de choisir indépendamment du système
 */
class ThemeModeManager(private val context: Context) {

    companion object {
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    // Mode de thème actuel (par défaut: SYSTEM)
    val themeMode: Flow<ThemeMode> = context.themeModeDataStore.data
        .map { preferences ->
            val modeName = preferences[THEME_MODE_KEY] ?: "SYSTEM"
            try {
                ThemeMode.valueOf(modeName)
            } catch (e: IllegalArgumentException) {
                ThemeMode.SYSTEM
            }
        }

    // Changer le mode de thème
    suspend fun setThemeMode(mode: ThemeMode) {
        context.themeModeDataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode.name
        }
    }

    // Méthode utilitaire pour déterminer si on doit utiliser le mode sombre
    suspend fun isDarkMode(): Boolean {
        val mode = themeMode.first()
        return when (mode) {
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
            ThemeMode.SYSTEM -> {
                // Vérifier le paramètre système
                val uiMode = context.resources.configuration.uiMode
                android.content.res.Configuration.UI_MODE_NIGHT_YES == 
                    (uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK)
            }
        }
    }
}
