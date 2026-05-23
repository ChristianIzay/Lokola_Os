package com.muana.lokola.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Thèmes culturels de Lokola OS
 * Inspirés des paysages et de la culture congolaise
 */
enum class CongoTheme(val displayName: String, val icon: String) {
    RUMBA("Rumba Congolaise", "🎵"),
    SAVANE("Savane Dorée", "🌅"),
    FLEUVE("Fleuve Congo", ""),
    FORET("Forêt Tropicale", "🌴")
}

/**
 * Configuration des couleurs pour chaque thème
 */
data class ThemeColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val accent: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val gradientStart: Color,
    val gradientEnd: Color,
    val dockBackground: Color
)

/**
 * Thème Rumba - Inspiré de la musique et de l'énergie congolaise
 */
val RumbaThemeColors = ThemeColors(
    primary = Color(0xFFE91E63),          // Rose vif de la rumba
    primaryVariant = Color(0xFFC2185B),
    secondary = Color(0xFFF7D618),        // Or des pagnes
    background = Color(0xFF1A1A2E),       // Nuit de Kinshasa
    surface = Color(0xFF16213E),
    accent = Color(0xFFFF5722),           // Énergie
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFB0BEC5),
    gradientStart = Color(0xFFE91E63),
    gradientEnd = Color(0xFFFF5722),
    dockBackground = Color(0xFF0F3460)
)

/**
 * Thème Savane - Inspiré des paysages dorés du Congo
 */
val SavaneThemeColors = ThemeColors(
    primary = Color(0xFFFF8F00),          // Or de la savane
    primaryVariant = Color(0xFFF57C00),
    secondary = Color(0xFF4CAF50),        // Vert des acacias
    background = Color(0xFFFFF8E1),       // Ciel doré
    surface = Color(0xFFFFFFFF),
    accent = Color(0xFF795548),           // Terre ocre
    textPrimary = Color(0xFF3E2723),
    textSecondary = Color(0xFF5D4037),
    gradientStart = Color(0xFFFF8F00),
    gradientEnd = Color(0xFFF57C00),
    dockBackground = Color(0xFFD7CCC8)
)

/**
 * Thème Fleuve - Inspiré du majestueux fleuve Congo
 */
val FleuveThemeColors = ThemeColors(
    primary = Color(0xFF0288D1),          // Bleu du fleuve
    primaryVariant = Color(0xFF01579B),
    secondary = Color(0xFF00BCD4),        // Cyan des eaux
    background = Color(0xFFE1F5FE),       // Ciel clair
    surface = Color(0xFFFFFFFF),
    accent = Color(0xFF0097A7),           // Profondeur
    textPrimary = Color(0xFF002171),
    textSecondary = Color(0xFF01579B),
    gradientStart = Color(0xFF0288D1),
    gradientEnd = Color(0xFF00BCD4),
    dockBackground = Color(0xFFB3E5FC)
)

/**
 * Thème Forêt - Inspiré de la forêt tropicale du Congo
 */
val ForetThemeColors = ThemeColors(
    primary = Color(0xFF2E7D32),          // Vert forêt
    primaryVariant = Color(0xFF1B5E20),
    secondary = Color(0xFF8BC34A),        // Vert clair
    background = Color(0xFFF1F8E9),       // Clair de forêt
    surface = Color(0xFFFFFFFF),
    accent = Color(0xFF558B2F),           // Mousse
    textPrimary = Color(0xFF1B5E20),
    textSecondary = Color(0xFF33691E),
    gradientStart = Color(0xFF2E7D32),
    gradientEnd = Color(0xFF4CAF50),
    dockBackground = Color(0xFFDCEDC8)
)

/**
 * Récupère les couleurs du thème sélectionné
 */
fun getThemeColors(theme: CongoTheme): ThemeColors {
    return when (theme) {
        CongoTheme.RUMBA -> RumbaThemeColors
        CongoTheme.SAVANE -> SavaneThemeColors
        CongoTheme.FLEUVE -> FleuveThemeColors
        CongoTheme.FORET -> ForetThemeColors
    }
}
