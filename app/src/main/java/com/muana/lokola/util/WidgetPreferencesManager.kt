package com.muana.lokola.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

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
        
        // Clé pour l'ordre des widgets (séparé par virgule) - legacy
        private val WIDGET_ORDER = stringPreferencesKey("widget_order")

        // Nouvelle clé pour le layout complet multi-pages (Phase 2)
        private val WIDGET_LAYOUT = stringPreferencesKey("widget_layout")

        // Clé pour la position du bouton Paramètres (Phase B)
        private val SETTINGS_BUTTON_POSITION = stringPreferencesKey("settings_button_position")
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

    // Ordre des widgets (par défaut: ordre standard) - legacy
    val widgetOrder: Flow<List<String>> = context.widgetPrefsDataStore.data
        .map { 
            val order = it[WIDGET_ORDER] ?: "rumba,news,calendar"
            order.split(",")
        }

    /**
     * Layout complet des widgets (page + position).
     * Utilisé pour le système multi-pages avec drag & drop.
     */
    val widgetPlacements: Flow<List<WidgetPlacement>> = context.widgetPrefsDataStore.data
        .map { preferences ->
            val layoutString = preferences[WIDGET_LAYOUT]
            if (layoutString != null) {
                parseWidgetLayout(layoutString)
            } else {
                // Première utilisation → répartition intelligente par défaut (2-2-1)
                getDefaultSmartDistribution()
            }
        }

    /**
     * Position actuelle du bouton Paramètres du launcher.
     * Par défaut : BOTTOM_RIGHT
     */
    val settingsButtonPosition: Flow<SettingsButtonPosition> = context.widgetPrefsDataStore.data
        .map { preferences ->
            val positionName = preferences[SETTINGS_BUTTON_POSITION] ?: "BOTTOM_RIGHT"
            try {
                SettingsButtonPosition.valueOf(positionName)
            } catch (e: IllegalArgumentException) {
                SettingsButtonPosition.BOTTOM_RIGHT
            }
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

    // ==================== NOUVELLES MÉTHODES PHASE 2 (Multi-pages + Drag & Drop) ====================

    /**
     * Déplace un widget vers une autre page et/ou position.
     * Utilisé pour le drag & drop complet (intra-page et inter-pages).
     */
    suspend fun moveWidget(widgetId: String, targetPage: Int, targetPosition: Int) {
        val currentPlacements = widgetPlacements.first().toMutableList()
        
        // Retirer l'ancien placement
        val widgetToMove = currentPlacements.find { it.id == widgetId } ?: return
        currentPlacements.remove(widgetToMove)
        
        // Réorganiser les positions sur l'ancienne page
        val oldPagePlacements = currentPlacements.filter { it.page == widgetToMove.page }
            .sortedBy { it.position }
            .mapIndexed { index, placement -> placement.copy(position = index) }
        
        currentPlacements.removeAll { it.page == widgetToMove.page }
        currentPlacements.addAll(oldPagePlacements)
        
        // Ajouter à la nouvelle page à la bonne position
        val newPagePlacements = currentPlacements.filter { it.page == targetPage }
            .sortedBy { it.position }
            .toMutableList()
        
        newPagePlacements.add(targetPosition, widgetToMove.copy(page = targetPage, position = targetPosition))
        
        // Réindexer les positions de la page cible
        val reindexedNewPage = newPagePlacements.mapIndexed { index, p -> p.copy(position = index) }
        
        currentPlacements.removeAll { it.page == targetPage }
        currentPlacements.addAll(reindexedNewPage)
        
        saveWidgetLayout(currentPlacements)
    }

    /**
     * Réorganise complètement les widgets sur une page donnée.
     */
    suspend fun reorderWidgetsOnPage(page: Int, orderedWidgetIds: List<String>) {
        val currentPlacements = widgetPlacements.first().toMutableList()
        
        val otherPages = currentPlacements.filter { it.page != page }
        
        val newPagePlacements = orderedWidgetIds.mapIndexed { index, id ->
            WidgetPlacement(id, page, index)
        }
        
        val newLayout = otherPages + newPagePlacements
        saveWidgetLayout(newLayout)
    }

    /**
     * Retourne les widgets d'une page spécifique, triés par position.
     */
    suspend fun getWidgetsForPage(page: Int): List<WidgetPlacement> {
        return widgetPlacements.first()
            .filter { it.page == page }
            .sortedBy { it.position }
    }

    private suspend fun saveWidgetLayout(placements: List<WidgetPlacement>) {
        val layoutString = placements.joinToString(",") { "${it.id}|${it.page}|${it.position}" }
        context.widgetPrefsDataStore.edit {
            it[WIDGET_LAYOUT] = layoutString
        }
    }

    private fun parseWidgetLayout(layoutString: String): List<WidgetPlacement> {
        return layoutString.split(",").mapNotNull { part ->
            val parts = part.split("|")
            if (parts.size == 3) {
                try {
                    WidgetPlacement(
                        id = parts[0],
                        page = parts[1].toInt(),
                        position = parts[2].toInt()
                    )
                } catch (e: Exception) {
                    null
                }
            } else null
        }
    }

    /**
     * Répartition intelligente par défaut (2-2-1)
     */
    private fun getDefaultSmartDistribution(): List<WidgetPlacement> {
        return listOf(
            // Page 1 (2 widgets)
            WidgetPlacement("rumba", 1, 0),
            WidgetPlacement("news", 1, 1),
            
            // Page 2 (2 widgets)
            WidgetPlacement("calendar", 2, 0),
            WidgetPlacement("proverb", 2, 1),
            
            // Page 3 (1 widget)
            WidgetPlacement("weather", 3, 0)
        )
    }

    /**
     * Réinitialise la disposition des widgets à la répartition intelligente par défaut.
     */
    suspend fun resetToDefaultDistribution() {
        saveWidgetLayout(getDefaultSmartDistribution())
    }

    /**
     * Change la position du bouton Paramètres du launcher.
     */
    suspend fun setSettingsButtonPosition(position: SettingsButtonPosition) {
        context.widgetPrefsDataStore.edit {
            it[SETTINGS_BUTTON_POSITION] = position.name
        }
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
 * Positions possibles pour le bouton Paramètres du launcher (Phase B)
 */
enum class SettingsButtonPosition {
    BOTTOM_RIGHT,
    BOTTOM_LEFT,
    TOP_RIGHT,
    TOP_LEFT
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

/**
 * Représente l'emplacement d'un widget dans le launcher multi-pages.
 */
data class WidgetPlacement(
    val id: String,
    val page: Int,      // 1, 2 ou 3
    val position: Int   // Ordre dans la page (0, 1, 2...)
)

// Fonction utilitaire pour convertir Flow en valeur (à utiliser dans ViewModel)
suspend fun <T> Flow<T>.value(): T {
    var result: T? = null
    collect { result = it }
    return result!!
}
