package com.muana.lokola.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Palette de Couleurs (Noir Pur, Marque et Couleur du Téléphone)
val BlackBackground = Color(0xFF000000)
val PhoneColor = Color(0xFFA39A82) // Couleur du téléphone A39A82
val BlueGlow = Color(0xFF007FFF)
val GoldAccent = Color(0xFFF7D618)
val DRCRed = Color(0xFFE53935)
val TextWhite = Color(0xFFF8F9FA)
val TextMuted = Color(0xFF8E9AA8)

val MontserratFontFamily = FontFamily.Default

@Composable
fun SplashScreen(
    onFinish: () -> Unit
) {
    var currentPhase by remember { mutableStateOf(SplashPhase.BootUp) }

    LaunchedEffect(Unit) {
        delay(2500)
        currentPhase = SplashPhase.Logo
        delay(2000)
        currentPhase = SplashPhase.ShuttingDown
        delay(2500)
        onFinish()
    }

    // Fond dynamique : Noir pour BootUp/Shutdown, couleur du téléphone A39A82 pour Logo
    val currentBg = when (currentPhase) {
        SplashPhase.BootUp -> BlackBackground
        SplashPhase.Logo -> PhoneColor
        SplashPhase.ShuttingDown -> BlackBackground
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(currentBg)
    ) {
        when (currentPhase) {
            SplashPhase.BootUp -> ModernBootUpAnimation()
            SplashPhase.Logo -> ModernLogoAnimation()
            SplashPhase.ShuttingDown -> ModernShutDownAnimation()
        }
    }
}

enum class SplashPhase {
    BootUp, Logo, ShuttingDown
}

@Composable
fun ModernBootUpAnimation() {
    val transition = rememberInfiniteTransition(label = "samsung_glow")

    val textAlpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        )
    )

    val textScale by transition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )

    val lineProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        lineProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(1800, easing = EaseOutCubic)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // DESIGNED IN DRC — en bas au milieu
        Text(
            text = "DESIGNED IN DRC",
            fontFamily = MontserratFontFamily,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextWhite.copy(alpha = 0.6f),
            letterSpacing = 4.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .graphicsLayer(alpha = textAlpha)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // WOTEC (Montserrat Bold)
            Text(
                text = "WOTEC",
                fontFamily = MontserratFontFamily,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                letterSpacing = 8.sp,
                modifier = Modifier.graphicsLayer(
                    scaleX = textScale,
                    scaleY = textScale,
                    alpha = textAlpha
                )
            )

            // Mobile (en dessous en petit caractère)
            Text(
                text = "Mobile",
                fontFamily = MontserratFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextWhite.copy(alpha = 0.7f),
                letterSpacing = 6.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .graphicsLayer(alpha = textAlpha)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Ligne HORIZONTALE dégradé RDC (Bleu → Jaune → Rouge)
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF1E2638))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(lineProgress.value)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(BlueGlow, GoldAccent, DRCRed)
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun ModernLogoAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_modern")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.97f, targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
        ) {
            Text(
                text = "LOKOLE",
                fontFamily = MontserratFontFamily,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .background(BlueGlow, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "S20",
                    fontFamily = MontserratFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "DESIGNED by Wotec Mobile",
                fontFamily = MontserratFontFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111),
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.width(40.dp).height(2.dp)
                    .background(Brush.horizontalGradient(listOf(BlueGlow, GoldAccent, DRCRed)))
            )
        }
    }
}

@Composable
fun ModernShutDownAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "drum_beat")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                1.0f at 0 with FastOutSlowInEasing
                1.08f at 200 with FastOutSlowInEasing
                1.03f at 400 with FastOutSlowInEasing
                1.08f at 600 with FastOutSlowInEasing
                1.0f at 1000 with FastOutSlowInEasing
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "scale"
    )

    val animateAlpha = remember { Animatable(1f) }
    LaunchedEffect(Unit) {
        delay(1500)
        animateAlpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(1000, easing = EaseInOutCubic)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer(
                alpha = animateAlpha.value,
                scaleX = scale,
                scaleY = scale
            )
        ) {
            LokoleImageFromBase64(
                modifier = Modifier
                    .size(240.dp)
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "À bientôt",
                fontFamily = MontserratFontFamily,
                fontSize = 20.sp,
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = "Lokola ezo lala...",
                fontFamily = MontserratFontFamily,
                fontSize = 12.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}