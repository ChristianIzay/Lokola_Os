package com.muana.lokola.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.widgetPrefsDataStore by preferencesDataStore(name = "widget_preferences")

/**
 * Gestionnaire de préférences des widgets
 * Permet à l'utilisateur d'activer/désactiver et configurer les widgets
 */
class WidgetPreferencesManager(private val context: Context) {

    companion object {
        // Clés pour les toggles on/off
        private val RUMBA_WIDGET_ENABLED = booleanPreferencesKey("rumba_widget_enabled")
        private val NEWS_WIDGET_ENABLED = booleanPreferencesKey("news_widget_enabled")
        private val CALENDAR_WIDGET_ENABLED = booleanPreferencesKey("calendar_widget_enabled")
        private val WEATHER_WIDGET_ENABLED = booleanPreferencesKey("weather_widget_enabled")
        private val PROVERB_WIDGET_ENABLED = booleanPreferencesKey("proverb_widget_enabled")
        private val RECIPES_WIDGET_ENABLED = booleanPreferencesKey("recipes_widget_enabled")
        
        // Clés pour la taille des widgets (compact/expanded)
        private val RUMBA_WIDGET_SIZE = stringPreferencesKey("rumba_widget_size")
        private val NEWS_WIDGET_SIZE = stringPreferencesKey("news_widget_size")
        private val CALENDAR_WIDGET_SIZE = stringPreferencesKey("calendar_widget_size")
        
        // Clé pour l'ordre des widgets (séparé par virgule)
        private val WIDGET_ORDER = stringPreferencesKey("widget_order")
    }

    // États d'activation des widgets (par défaut: tous activés)
    val isRumbaWidgetEnabled: Flow<Boolean> = context.widgetPrefsDataStore.data
        .map { it[RUMBA_WIDGET_ENABLED] ?: true }

    val isNewsWidgetEnabled: Flow<Boolean> = context.widgetPrefsDataStore.data
        .map { it[NEWS_WIDGET_ENABLED] ?: true }

    val isCalendarWidgetEnabled: Flow<Boolean> = context.widgetPrefsDataStore.data
        .map { it[CALENDAR_WIDGET_ENABLED] ?: true }

    val isWeatherWidgetEnabled: Flow<Boolean> = context.widgetPrefsDataStore.data
        .map { it[WEATHER_WIDGET_ENABLED] ?: false }

    val isProverbWidgetEnabled: Flow<Boolean> = context.widgetPrefsDataStore.data
        .map { it[PROVERB_WIDGET_ENABLED] ?: false }

    val isRecipesWidgetEnabled: Flow<Boolean> = context.widgetPrefsDataStore.data
        .map { it[RECIPES_WIDGET_ENABLED] ?: false }

    // Tailles des widgets (par défaut: expanded)
    val rumbaWidgetSize: Flow<WidgetSize> = context.widgetPrefsDataStore.data
        .map { 
            val size = it[RUMBA_WIDGET_SIZE] ?: "expanded"
            WidgetSize.valueOf(size.uppercase())
        }

    val newsWidgetSize: Flow<WidgetSize> = context.widgetPrefsDataStore.data
        .map { 
            val size = it[NEWS_WIDGET_SIZE] ?: "expanded"
            WidgetSize.valueOf(size.uppercase())
        }

    val calendarWidgetSize: Flow<WidgetSize> = context.widgetPrefsDataStore.data
        .map { 
            val size = it[CALENDAR_WIDGET_SIZE] ?: "expanded"
            WidgetSize.valueOf(size.uppercase())
        }

    // Ordre des widgets (par défaut: ordre standard)
    val widgetOrder: Flow<List<String>> = context.widgetPrefsDataStore.data
        .map { 
            val order = it[WIDGET_ORDER] ?: "rumba,news,calendar"
            order.split(",")
        }

    // Méthodes pour modifier les préférences
    suspend fun setRumbaWidgetEnabled(enabled: Boolean) {
        context.widgetPrefsDataStore.edit { it[RUMBA_WIDGET_ENABLED] = enabled }
    }

    suspend fun setNewsWidgetEnabled(enabled: Boolean) {
        context.widgetPrefsDataStore.edit { it[NEWS_WIDGET_ENABLED] = enabled }
    }

    suspend fun setCalendarWidgetEnabled(enabled: Boolean) {
        context.widgetPrefsDataStore.edit { it[CALENDAR_WIDGET_ENABLED] = enabled }
    }

    suspend fun setWeatherWidgetEnabled(enabled: Boolean) {
        context.widgetPrefsDataStore.edit { it[WEATHER_WIDGET_ENABLED] = enabled }
    }

    suspend fun setProverbWidgetEnabled(enabled: Boolean) {
        context.widgetPrefsDataStore.edit { it[PROVERB_WIDGET_ENABLED] = enabled }
    }

    suspend fun setRecipesWidgetEnabled(enabled: Boolean) {
        context.widgetPrefsDataStore.edit { it[RECIPES_WIDGET_ENABLED] = enabled }
    }

    suspend fun setRumbaWidgetSize(size: WidgetSize) {
        context.widgetPrefsDataStore.edit { it[RUMBA_WIDGET_SIZE] = size.name.lowercase() }
    }

    suspend fun setNewsWidgetSize(size: WidgetSize) {
        context.widgetPrefsDataStore.edit { it[NEWS_WIDGET_SIZE] = size.name.lowercase() }
    }

    suspend fun setCalendarWidgetSize(size: WidgetSize) {
        context.widgetPrefsDataStore.edit { it[CALENDAR_WIDGET_SIZE] = size.name.lowercase() }
    }

    suspend fun setWidgetOrder(order: List<String>) {
        context.widgetPrefsDataStore.edit { it[WIDGET_ORDER] = order.joinToString(",") }
    }

    // Méthode utilitaire pour obtenir tous les états
    suspend fun getAllWidgetStates(): WidgetStates {
        return WidgetStates(
            rumbaEnabled = isRumbaWidgetEnabled.value(),
            newsEnabled = isNewsWidgetEnabled.value(),
            calendarEnabled = isCalendarWidgetEnabled.value(),
            weatherEnabled = isWeatherWidgetEnabled.value(),
            proverbEnabled = isProverbWidgetEnabled.value(),
            recipesEnabled = isRecipesWidgetEnabled.value(),
            rumbaSize = rumbaWidgetSize.value(),
            newsSize = newsWidgetSize.value(),
            calendarSize = calendarWidgetSize.value(),
            order = widgetOrder.value()
        )
    }
}

/**
 * Enum pour les tailles de widgets
 */
enum class WidgetSize {
    COMPACT,    // Version réduite
    EXPANDED    // Version complète
}

/**
 * Data class regroupant tous les états des widgets
 */
data class WidgetStates(
    val rumbaEnabled: Boolean,
    val newsEnabled: Boolean,
    val calendarEnabled: Boolean,
    val weatherEnabled: Boolean,
    val proverbEnabled: Boolean,
    val recipesEnabled: Boolean,
    val rumbaSize: WidgetSize,
    val newsSize: WidgetSize,
    val calendarSize: WidgetSize,
    val order: List<String>
)

// Fonction utilitaire pour convertir Flow en valeur (à utiliser dans ViewModel)
suspend fun <T> Flow<T>.value(): T {
    var result: T? = null
    collect { result = it }
    return result!!
}
