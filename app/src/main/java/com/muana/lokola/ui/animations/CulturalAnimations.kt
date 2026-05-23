package com.muana.lokola.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Animations fluides inspirées des mouvements de danse congolaise
 * Rumba et Ndombolo caractérisées par des transitions douces et rythmiques
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
 * Animation de rebond rythmique - Inspirée du ndombolo
 * Mouvement énergique avec rebond
 */
fun ndomboloBounce(): FiniteAnimationSpec<Float> = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow
)

/**
 * Modifier pour animation d'entrée fluide (rumba style)
 */
@Composable
fun Modifier.rumbaEnterAnimation(
    visible: Boolean,
    initialScale: Float = 0.8f,
    targetScale: Float = 1f
): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (visible) targetScale else initialScale,
        animationSpec = rumbaTransition(),
        label = "rumba_scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = rumbaTransition(),
        label = "rumba_alpha"
    )
    
    return this
        .scale(scale)
        .alpha(alpha)
}

/**
 * Modifier pour animation de pulsation continue (rythme rumba)
 */
@Composable
fun Modifier.rumbaPulse(
    initialValue: Float = 0.95f,
    targetValue: Float = 1.05f,
    durationMillis: Int = 2000
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "rumba_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    
    return this.scale(scale)
}

/**
 * Modifier pour animation de vague (thème Fleuve)
 * Effet ondulant continu
 */
@Composable
fun Modifier.waveAnimation(
    amplitude: Float = 10f,
    period: Int = 3000
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -amplitude,
        targetValue = amplitude,
        animationSpec = infiniteRepeatable(
            animation = tween(period, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave_offset"
    )
    
    return this.graphicsLayer(translationY = offsetY)
}

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
        animationSpec = rumbaTransition()
    ) + fadeIn(animationSpec = rumbaTransition())
}

@Composable
fun rememberRumbaPageExitTransition(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = rumbaTransition()
    ) + fadeOut(animationSpec = rumbaTransition())
}
