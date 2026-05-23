package com.muana.lokola.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.ui.theme.ThemeColors

/**
 * Widget Rumba Congolaise - Inspiré du patrimoine UNESCO
 * Affiche des informations sur la rumba congolaise avec animations fluides
 */
@Composable
fun RumbaWidget(
    themeColors: ThemeColors,
    modifier: Modifier = Modifier
) {
    var currentSongIndex by remember { mutableStateOf(0) }
    
    // Liste de chansons emblématiques de rumba congolaise
    val rumbaSongs = remember {
        listOf(
            "Indépendance Cha Cha - Le Grand Kallé",
            "Mario - Franco Luambo",
            "Cécilia - Tabu Ley Rochereau",
            "Kelele - Papa Wemba",
            "Mabe Ya Motema - Koffi Olomide"
        )
    }
    
    // Animation de transition fluide inspirée de la rumba
    val infiniteTransition = rememberInfiniteTransition(label = "rumba")
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            themeColors.gradientStart.copy(alpha = 0.3f),
                            themeColors.gradientEnd.copy(alpha = 0.3f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header avec badge UNESCO
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🎵",
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Rumba Congolaise",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.textPrimary
                        )
                    }
                    
                    Badge(
                        containerColor = themeColors.secondary,
                        contentColor = themeColors.textPrimary
                    ) {
                        Text("UNESCO", fontSize = 10.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Chanson en rotation avec animation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rumbaSongs[currentSongIndex],
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = themeColors.textPrimary.copy(alpha = animatedAlpha),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
                
                // Indicateurs de progression
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(rumbaSongs.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (index == currentSongIndex) 8.dp else 6.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(
                                    if (index == currentSongIndex) 
                                        themeColors.primary 
                                    else 
                                        themeColors.textSecondary.copy(alpha = 0.3f)
                                )
                        )
                    }
                }
            }
        }
    }
    
    // Rotation automatique des chansons
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(4000)
            currentSongIndex = (currentSongIndex + 1) % rumbaSongs.size
        }
    }
}
