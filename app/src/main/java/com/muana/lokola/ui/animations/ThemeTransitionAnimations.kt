package com.muana.lokola.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

/**
 * Animations de transition pour le changement de mode thème (clair/sombre)
 * Transitions fluides et élégantes pour une expérience utilisateur optimale
 */

/**
 * Animation de transition de couleur fluide
 * Utilise l'interpolation linéaire pour des transitions douces entre les couleurs
 */
@Composable
fun animateThemeColorAsState(
    targetValue: Color,
    animationSpec: AnimationSpec<Color> = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    ),
    label: String = "themeColorAnimation"
): State<Color> {
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = androidx.compose.animation.core.Color.VectorConverter,
        animationSpec = animationSpec,
        label = label
    )
}

/**
 * Modifier pour animation de fondu lors du changement de thème
 * Applique un fade in/out subtil pendant la transition
 */
@Composable
fun Modifier.themeTransitionFade(
    isDarkMode: Boolean,
    durationMillis: Int = 250
): Modifier {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = LinearEasing
        ),
        label = "themeFade"
    )
    
    return this.alpha(alpha)
}

/**
 * Animation de transition pour les arrière-plans
 * Crée un effet de crossfade entre les modes clair et sombre
 */
@Composable
fun animateBackgroundAsState(
    lightColor: Color,
    darkColor: Color,
    isDarkMode: Boolean,
    durationMillis: Int = 350
): State<Color> {
    val targetColor = if (isDarkMode) darkColor else lightColor
    
    return animateValueAsState(
        targetValue = targetColor,
        typeConverter = androidx.compose.animation.core.Color.VectorConverter,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "backgroundTransition"
    )
}

/**
 * Animation de transition pour les textes
 * Adapte la couleur du texte avec une transition douce
 */
@Composable
fun animateTextColorAsState(
    lightTextColor: Color,
    darkTextColor: Color,
    isDarkMode: Boolean,
    durationMillis: Int = 300
): State<Color> {
    val targetColor = if (isDarkMode) darkTextColor else lightTextColor
    
    return animateValueAsState(
        targetValue = targetColor,
        typeConverter = androidx.compose.animation.core.Color.VectorConverter,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "textColorTransition"
    )
}

/**
 * Animation de transition pour les dégradés
 * Interpole entre deux couleurs de dégradé selon le mode
 */
@Composable
fun animateGradientAsState(
    lightStart: Color,
    lightEnd: Color,
    darkStart: Color,
    darkEnd: Color,
    isDarkMode: Boolean,
    durationMillis: Int = 400
): Pair<State<Color>, State<Color>> {
    val startColor = if (isDarkMode) darkStart else lightStart
    val endColor = if (isDarkMode) darkEnd else lightEnd
    
    val animatedStart = animateValueAsState(
        targetValue = startColor,
        typeConverter = androidx.compose.animation.core.Color.VectorConverter,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "gradientStart"
    )
    
    val animatedEnd = animateValueAsState(
        targetValue = endColor,
        typeConverter = androidx.compose.animation.core.Color.VectorConverter,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "gradientEnd"
    )
    
    return Pair(animatedStart, animatedEnd)
}

/**
 * Animation d'échelle subtile pour les cartes/widgets lors du changement de thème
 * Effet de "respiration" pendant la transition
 */
@Composable
fun Modifier.themeTransitionScale(
    isDarkMode: Boolean,
    scaleAmount: Float = 0.02f,
    durationMillis: Int = 300
): Modifier {
    val targetScale = if (isDarkMode) 1f + scaleAmount else 1f
    
    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "themeScale"
    )
    
    return this then androidx.compose.ui.graphics.graphicsLayer(scaleX = scale, scaleY = scale)
}

/**
 * Animation combinée pour une transition complète de thème
 * Combine fade, scale et color transition pour un effet professionnel
 */
data class ThemeTransitionState(
    val alpha: Float,
    val scale: Float,
    val backgroundColor: Color,
    val textColor: Color
)

@Composable
fun rememberThemeTransition(
    isDarkMode: Boolean,
    lightBackground: Color,
    darkBackground: Color,
    lightText: Color,
    darkText: Color,
    durationMillis: Int = 350
): ThemeTransitionState {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis, easing = LinearEasing),
        label = "transitionAlpha"
    )
    
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing),
        label = "transitionScale"
    )
    
    val backgroundColor by animateValueAsState(
        targetValue = if (isDarkMode) darkBackground else lightBackground,
        typeConverter = androidx.compose.animation.core.Color.VectorConverter,
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing),
        label = "transitionBackground"
    )
    
    val textColor by animateValueAsState(
        targetValue = if (isDarkMode) darkText else lightText,
        typeConverter = androidx.compose.animation.core.Color.VectorConverter,
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing),
        label = "transitionText"
    )
    
    return ThemeTransitionState(
        alpha = alpha,
        scale = scale,
        backgroundColor = backgroundColor,
        textColor = textColor
    )
}

/**
 * Easing personnalisé pour des transitions naturelles inspirées de la rumba
 * Mouvement fluide avec accélération/décélération organique
 */
val RumbaEasing: Easing = Easing { fraction ->
    // Courbe en S douce, comme un mouvement de danse
    when {
        fraction < 0.5f -> {
            val t = fraction * 2f
            0.5f * t * t * t
        }
        else -> {
            val t = (fraction - 0.5f) * 2f
            0.5f * (2 - t * t * (2 - t))
        }
    }
}

/**
 * Animation de transition avec easing rumba
 */
@Composable
fun animateThemeColorRumbaAsState(
    targetValue: Color,
    durationMillis: Int = 400,
    label: String = "rumbaThemeColor"
): State<Color> {
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = androidx.compose.animation.core.Color.VectorConverter,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = RumbaEasing
        ),
        label = label
    )
}
