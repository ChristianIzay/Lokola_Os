package com.muana.lokola.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Lokola Heritage Design System
 * Typographie + Couleurs + Motion inspirés de la culture congolaise
 */

// ============================================
// 1. TYPOGRAPHIE CULTURELLE
// ============================================

object CongoTypography {
    val KubaDisplayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Black,
        fontSize = 40.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.8).sp
    )

    val KubaHeadline = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.4).sp
    )

    val RumbaBodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.2.sp
    )

    val RumbaBody = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    )

    val NdomboloLabel = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.6.sp
    )
}

// ============================================
// 2. SYSTÈME DE COULEURS CULTURELLES
// ============================================

object CongoColors {
    val KubaGold = Color(0xFFF9A825)

    object Rumba {
        val primary = Color(0xFFB71C1C)
        val secondary = Color(0xFFF9A825)
        val accent = Color(0xFFFFEB3B)
        val background = Color(0xFF1A1A1A)
        val surface = Color(0xFF2C2C2C)
    }

    object Savane {
        val primary = Color(0xFFE65100)
        val secondary = Color(0xFF8D6E63)
        val accent = Color(0xFFFFE082)
        val background = Color(0xFFF5E8C7)
        val surface = Color(0xFFFFF8E1)
    }

    object Fleuve {
        val primary = Color(0xFF0D47A1)
        val secondary = Color(0xFF42A5F5)
        val accent = Color(0xFFC5B358)
        val background = Color(0xFF0A192F)
        val surface = Color(0xFF112240)
    }

    object Foret {
        val primary = Color(0xFF1B5E20)
        val secondary = Color(0xFF4CAF50)
        val accent = Color(0xFFA1887F)
        val background = Color(0xFF2E2E1F)
        val surface = Color(0xFF3C3C2F)
    }
}

// ============================================
// 3. MOTION SYSTEM
// ============================================

object CongoMotion {
    const val RiverWaveDuration = 2400
}

@Composable
fun Modifier.kubaPulse(): Modifier {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "kuba_pulse"
    )
    return this.scale(scale)
}

@Composable
fun Modifier.rumbaGroove(): Modifier {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = 0.45f, stiffness = 320f),
        label = "rumba_groove"
    )
    return this.scale(scale)
}

@Composable
fun Modifier.riverFlow(): Modifier {
    val offset by animateFloatAsState(
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(CongoMotion.RiverWaveDuration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "river_flow"
    )
    return this.graphicsLayer { translationY = offset * 6f }
}

@Composable
fun Modifier.maskReveal(visible: Boolean = true): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.85f,
        animationSpec = tween(720, easing = FastOutSlowInEasing),
        label = "mask_scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(720),
        label = "mask_alpha"
    )
    return this.scale(scale).alpha(alpha)
}

@Composable
fun Modifier.ndomboloBounce(pressed: Boolean = false): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 280f),
        label = "ndombolo_bounce"
    )
    return this.scale(scale)
}

// ============================================
// 4. HELPER
// ============================================

fun getCongoColorsForTheme(theme: CongoTheme): ThemeColors {
    return when (theme) {
        CongoTheme.RUMBA -> ThemeColors(
            primary = CongoColors.Rumba.primary,
            primaryVariant = CongoColors.Rumba.primary.copy(alpha = 0.8f),
            secondary = CongoColors.Rumba.secondary,
            background = CongoColors.Rumba.background,
            surface = CongoColors.Rumba.surface,
            accent = CongoColors.Rumba.accent,
            textPrimary = Color(0xFFE0E0E0),
            textSecondary = Color(0xFFBDBDBD),
            gradientStart = CongoColors.Rumba.primary,
            gradientEnd = CongoColors.Rumba.accent,
            dockBackground = Color(0xFF1F1F1F)
        )
        CongoTheme.SAVANE -> ThemeColors(
            primary = CongoColors.Savane.primary,
            primaryVariant = CongoColors.Savane.primary.copy(alpha = 0.8f),
            secondary = CongoColors.Savane.secondary,
            background = CongoColors.Savane.background,
            surface = CongoColors.Savane.surface,
            accent = CongoColors.Savane.accent,
            textPrimary = Color(0xFF3E2723),
            textSecondary = Color(0xFF5D4037),
            gradientStart = CongoColors.Savane.primary,
            gradientEnd = CongoColors.Savane.accent,
            dockBackground = Color(0xFFF5E8C7)
        )
        CongoTheme.FLEUVE -> ThemeColors(
            primary = CongoColors.Fleuve.primary,
            primaryVariant = CongoColors.Fleuve.primary.copy(alpha = 0.8f),
            secondary = CongoColors.Fleuve.secondary,
            background = CongoColors.Fleuve.background,
            surface = CongoColors.Fleuve.surface,
            accent = CongoColors.Fleuve.accent,
            textPrimary = Color(0xFFE3F2FD),
            textSecondary = Color(0xFF90CAF9),
            gradientStart = CongoColors.Fleuve.primary,
            gradientEnd = CongoColors.Fleuve.accent,
            dockBackground = Color(0xFF0F2847)
        )
        CongoTheme.FORET -> ThemeColors(
            primary = CongoColors.Foret.primary,
            primaryVariant = CongoColors.Foret.primary.copy(alpha = 0.8f),
            secondary = CongoColors.Foret.secondary,
            background = CongoColors.Foret.background,
            surface = CongoColors.Foret.surface,
            accent = CongoColors.Foret.accent,
            textPrimary = Color(0xFFE8F5E9),
            textSecondary = Color(0xFFA5D6A7),
            gradientStart = CongoColors.Foret.primary,
            gradientEnd = CongoColors.Foret.accent,
            dockBackground = Color(0xFF3C3C2F)
        )
    }
}

// ============================================
// 5. CENTRAL OBJECT
// ============================================

object LokolaHeritage {
    val typography = CongoTypography
    val colors = CongoColors
    val motion = CongoMotion

    fun colorsFor(theme: CongoTheme): ThemeColors = getCongoColorsForTheme(theme)
}
