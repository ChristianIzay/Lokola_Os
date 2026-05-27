package com.muana.lokola.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
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
 * Supporte les modes clair et sombre
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
    val dockBackground: Color,
    val isDarkTheme: Boolean = false  // Indicateur pour adaptations supplémentaires
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
 * Adapte automatiquement selon le mode sombre/clair
 */
@Composable
fun getThemeColors(theme: CongoTheme): ThemeColors {
    val isDarkMode = isSystemInDarkTheme()
    val baseColors = when (theme) {
        CongoTheme.RUMBA -> if (isDarkMode) RumbaThemeColorsDark else RumbaThemeColors
        CongoTheme.SAVANE -> if (isDarkMode) SavaneThemeColorsDark else SavaneThemeColors
        CongoTheme.FLEUVE -> if (isDarkMode) FleuveThemeColorsDark else FleuveThemeColors
        CongoTheme.FORET -> if (isDarkMode) ForetThemeColorsDark else ForetThemeColors
    }
    
    return baseColors.copy(isDarkTheme = isDarkMode)
}

/**
 * Versions sombres des thèmes culturels
 * Optimisées pour la lisibilité et le confort visuel en mode sombre
 */

// Thème Rumba - Mode Sombre
val RumbaThemeColorsDark = ThemeColors(
    primary = Color(0xFFFF6B9D),          // Rose plus lumineux
    primaryVariant = Color(0xFFE91E63),
    secondary = Color(0xFFFFDB74),        // Or plus brillant
    background = Color(0xFF0D0D1A),       // Nuit profonde
    surface = Color(0xFF1A1A2E),
    accent = Color(0xFFFF7043),           // Orange vif
    textPrimary = Color(0xFFE0E0E0),      // Texte clair
    textSecondary = Color(0xFFB0BEC5),
    gradientStart = Color(0xFFFF6B9D),
    gradientEnd = Color(0xFFFF7043),
    dockBackground = Color(0xFF0F3460),
    isDarkTheme = true
)

// Thème Savane - Mode Sombre
val SavaneThemeColorsDark = ThemeColors(
    primary = Color(0xFFFFB74D),          // Or adaptatif
    primaryVariant = Color(0xFFFF8F00),
    secondary = Color(0xFF81C784),        // Vert plus doux
    background = Color(0xFF1A1410),       // Terre sombre
    surface = Color(0xFF2C2416),
    accent = Color(0xFFA1887F),           // Ocre clair
    textPrimary = Color(0xFFEFEBE9),      // Texte très clair
    textSecondary = Color(0xFFBCAAA4),
    gradientStart = Color(0xFFFFB74D),
    gradientEnd = Color(0xFFFF8F00),
    dockBackground = Color(0xFF3E2723),
    isDarkTheme = true
)

// Thème Fleuve - Mode Sombre
val FleuveThemeColorsDark = ThemeColors(
    primary = Color(0xFF4FC3F7),          // Bleu ciel lumineux
    primaryVariant = Color(0xFF0288D1),
    secondary = Color(0xFF4DD0E1),        // Cyan brillant
    background = Color(0xFF0D1B2A),       // Eau profonde
    surface = Color(0xFF1B2838),
    accent = Color(0xFF29B6F6),           // Bleu clair
    textPrimary = Color(0xFFE1F5FE),      // Texte bleu très clair
    textSecondary = Color(0xFFB3E5FC),
    gradientStart = Color(0xFF4FC3F7),
    gradientEnd = Color(0xFF4DD0E1),
    dockBackground = Color(0xFF01579B),
    isDarkTheme = true
)

// Thème Forêt - Mode Sombre
val ForetThemeColorsDark = ThemeColors(
    primary = Color(0xFF66BB6A),          // Vert lumineux
    primaryVariant = Color(0xFF2E7D32),
    secondary = Color(0xFFAED581),        // Vert clair
    background = Color(0xFF0D1F0F),       // Forêt nocturne
    surface = Color(0xFF1B2E1D),
    accent = Color(0xFF81C784),           // Vert mousse clair
    textPrimary = Color(0xFFE8F5E9),      // Texte vert très clair
    textSecondary = Color(0xFFC8E6C9),
    gradientStart = Color(0xFF66BB6A),
    gradientEnd = Color(0xFF81C784),
    dockBackground = Color(0xFF1B5E20),
    isDarkTheme = true
)
