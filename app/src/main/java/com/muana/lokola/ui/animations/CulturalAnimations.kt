package com.muana.lokola.ui.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import com.muana.lokola.ui.theme.kubaPulse
import com.muana.lokola.ui.theme.maskReveal
import com.muana.lokola.ui.theme.riverFlow

/**
 * === LEGACY / ADVANCED CULTURAL ANIMATIONS ===
 *
 * Most common cultural animations have been moved to the centralized
 * **Lokola Heritage Design System** (CongoDesignSystem.kt):
 *
 *  - kubaPulse()
 *  - rumbaGroove()
 *  - riverFlow()
 *  - maskReveal()
 *  - ndomboloBounce()
 *
 * This file now contains:
 *  - More complex / specialized animations (particles, shimmer, rotation, forest grow...)
 *  - Page transition helpers
 *  - Deprecated wrappers for backward compatibility
 *
 * Prefer using the Design System versions whenever possible.
 */

/**
 * Animation de transition fluide - Inspirée du mouvement de la rumba
 * Crée un effet de fondu enchaîné élégant
 */
fun rumbaTransition(): FiniteAnimationSpec<Float> = tween(
    durationMillis = 800,
    easing = FastOutSlowInEasing
)

/**
 * Animation de transition pour les offsets (IntOffset)
 * Utilisée pour les transitions de slide horizontales/verticales
 */
fun rumbaOffsetTransition(): FiniteAnimationSpec<IntOffset> = tween(
    durationMillis = 800,
    easing = FastOutSlowInEasing
)

@Deprecated(
    message = "Use the Modifier version ndomboloBounce(pressed) from CongoDesignSystem instead",
    replaceWith = ReplaceWith("ndomboloBounce(pressed)", "com.muana.lokola.ui.theme.CongoDesignSystem")
)
fun ndomboloBounce(): FiniteAnimationSpec<Float> = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow
)

@Deprecated(
    message = "Use maskReveal() or rumbaGroove() from CongoDesignSystem instead",
    replaceWith = ReplaceWith("maskReveal(visible)", "com.muana.lokola.ui.theme.CongoDesignSystem")
)
@Composable
fun Modifier.rumbaEnterAnimation(
    visible: Boolean,
    initialScale: Float = 0.8f,
    targetScale: Float = 1f
): Modifier = this.maskReveal(visible) // fallback to new system

@Deprecated(
    message = "Use kubaPulse() or rumbaGroove() from CongoDesignSystem (Lokola Heritage) instead",
    replaceWith = ReplaceWith("kubaPulse()", "com.muana.lokola.ui.theme.CongoDesignSystem")
)
@Composable
fun Modifier.rumbaPulse(
    initialValue: Float = 0.95f,
    targetValue: Float = 1.05f,
    durationMillis: Int = 2000
): Modifier = this.kubaPulse() // redirects to new Design System version

@Deprecated(
    message = "Use riverFlow() from CongoDesignSystem instead (Lokola Heritage)",
    replaceWith = ReplaceWith("riverFlow()", "com.muana.lokola.ui.theme.CongoDesignSystem")
)
@Composable
fun Modifier.waveAnimation(
    amplitude: Float = 10f,
    period: Int = 3000
): Modifier = this.riverFlow()

/**
 * Modifier pour particules dorées flottantes (thème Savane)
 */
@Composable
fun Modifier.goldenParticles(
    count: Int = 5,
    size: Float = 4f
): Modifier {
    val particles = remember(count) {
        List(count) { index ->
            ParticleData(
                delay = index * 500,
                startX = (index % 3) * 100f,
                duration = 4000 + (index * 300)
            )
        }
    }
    
    var offsetY by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(100)
            offsetY -= 2f
            offsetX += 0.5f
            if (offsetY < -100f) {
                offsetY = 0f
                offsetX = 0f
            }
        }
    }
    
    return this.graphicsLayer(
        translationX = offsetX,
        translationY = offsetY
    )
}

data class ParticleData(
    val delay: Int,
    val startX: Float,
    val duration: Int
)

/**
 * Animation de rotation douce (mouvement circulaire de la rumba)
 */
@Composable
fun Modifier.rumbaRotation(
    durationMillis: Int = 8000,
    maxAngle: Float = 5f
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "rumba_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -maxAngle,
        targetValue = maxAngle,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation_angle"
    )
    
    return this.graphicsLayer(rotationZ = rotation)
}

/**
 * Animation de shimmer élégant (reflets sur le fleuve)
 */
@Composable
fun Modifier.riverShimmer(
    durationMillis: Int = 2000
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )
    
    return this.alpha(alpha)
}

/**
 * Animation de croissance organique (nature/forêt)
 */
@Composable
fun Modifier.forestGrow(visible: Boolean): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "forest_grow"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(600),
        label = "forest_alpha"
    )
    
    return this
        .scale(scale)
        .alpha(alpha)
}

/**
 * Transition entre écrans style rumba (fluide et élégante)
 */
@Composable
fun rememberRumbaPageTransition(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = rumbaOffsetTransition()
    ) + fadeIn(animationSpec = rumbaTransition())
}

@Composable
fun rememberRumbaPageExitTransition(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = rumbaOffsetTransition()
    ) + fadeOut(animationSpec = rumbaTransition())
}
