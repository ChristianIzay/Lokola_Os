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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.ui.theme.ThemeColors
import java.time.LocalDate

/**
 * Widget Proverbes Congolais - Sagesse ancestrale quotidienne
 */
@Composable
fun ProverbWidget(
    themeColors: ThemeColors,
    modifier: Modifier = Modifier
) {
    // Collection de proverbes congolais authentiques
    val proverbs = remember {
        listOf(
            ProverbItem(
                lingala = "Motema moko ekokaka te na nzela mibale",
                french = "Un cœur ne peut pas suivre deux chemins",
                meaning = "Il faut choisir une direction et s'y tenir"
            ),
            ProverbItem(
                lingala = "Nzoka ezalaka te kaka na mbisi",
                french = "L'écaille n'est pas seulement chez le poisson",
                meaning = "Chacun a ses défauts et qualités"
            ),
            ProverbItem(
                lingala = "Mwana ya mobali akolaka na libanda",
                french = "L'enfant d'un homme grandit à l'extérieur",
                meaning = "L'éducation vient aussi de la communauté"
            ),
            ProverbItem(
                lingala = "Bato bakufaka kaka na molimo",
                french = "Les gens meurent seulement dans l'esprit",
                meaning = "La mémoire garde les défunts vivants"
            ),
            ProverbItem(
                lingala = "Soki osengeli kokoma, kotuna motuna",
                french = "Si tu dois écrire, pose une question",
                meaning = "La curiosité mène à la connaissance"
            ),
            ProverbItem(
                lingala = "Likambo ya kokamwa ezali te",
                french = "Il n'y a rien d'impossible",
                meaning = "Avec la volonté, tout est possible"
            ),
            ProverbItem(
                lingala = "Mbula ikunza, mvula ikunza",
                french = "La pluie tombe, l'eau coule",
                meaning = "Les choses suivent leur cours naturel"
            )
        )
    }
    
    // Sélection du proverbe basé sur la date (un par jour)
    val currentDate = remember { LocalDate.now() }
    val proverbIndex = currentDate.dayOfYear % proverbs.size
    val todayProverb = proverbs[proverbIndex]
    
    // Animation douce d'apparition
    val infiniteTransition = rememberInfiniteTransition(label = "proverb")
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fade"
    )
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            themeColors.secondary.copy(alpha = 0.15f),
                            themeColors.surface
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "💬",
                        fontSize = 28.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Proverbe du Jour",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.textPrimary
                    )
                }
                
                // Proverbe en Lingala
                Text(
                    text = "\"${todayProverb.lingala}\"",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Traduction française
                Text(
                    text = todayProverb.french,
                    fontSize = 14.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = themeColors.textPrimary.copy(alpha = animatedAlpha),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // Signification
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = themeColors.background.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "📖 ${todayProverb.meaning}",
                        fontSize = 12.sp,
                        color = themeColors.textSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

data class ProverbItem(
    val lingala: String,
    val french: String,
    val meaning: String
)
