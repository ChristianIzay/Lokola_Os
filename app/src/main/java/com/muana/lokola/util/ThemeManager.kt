package com.muana.lokola.util

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.muana.lokola.ui.theme.CongoTheme

private val Context.themeDataStore by preferencesDataStore(name = "theme_prefs")

class ThemeManager(private val context: Context) {

    companion object {
        private val THEME_KEY = stringPreferencesKey("selected_theme")
    }

    // Thème actuel (par défaut: FLEUVE)
    val currentTheme: Flow<CongoTheme> = context.themeDataStore.data
        .map { preferences ->
            val themeName = preferences[THEME_KEY] ?: "FLEUVE"
            CongoTheme.valueOf(themeName)
        }

    // Changer le thème
    suspend fun setTheme(theme: CongoTheme) {
        context.themeDataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.name
        }
    }
}
