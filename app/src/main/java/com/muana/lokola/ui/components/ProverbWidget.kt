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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.R
import com.muana.lokola.ui.theme.*
import java.time.LocalDate

/**
 * Widget Proverbes Congolais - Sagesse ancestrale quotidienne
 */
@Composable
fun ProverbWidget(
    themeColors: ThemeColors,
    currentLanguage: String = "fr",
    modifier: Modifier = Modifier
) {
    // Collection de proverbes congolais authentiques
    val proverbs = remember {
        listOf(
            ProverbItem(
                lingala = "Motema moko ekokaka te na nzela mibale",
                french = "Un cœur ne peut pas suivre deux chemins",
                meaningFr = "Il faut choisir une direction et s'y tenir",
                meaningLing = "Esengeli kopɔní nzela mɔ́kɔ́ mpe kobómbá yango"
            ),
            ProverbItem(
                lingala = "Nzoka ezalaka te kaka na mbisi",
                french = "L'écaille n'est pas seulement chez le poisson",
                meaningFr = "Chacun a ses défauts et qualités",
                meaningLing = "Moto nyɔ́nsɔ azali na mabé mpe malámu na yé"
            ),
            ProverbItem(
                lingala = "Mwana ya mobali akolaka na libanda",
                french = "L'enfant d'un homme grandit à l'extérieur",
                meaningFr = "L'éducation vient aussi de la communauté",
                meaningLing = "Boyébi bwa mwana ezali mpe na lisungi ya mbóka"
            ),
            ProverbItem(
                lingala = "Bato bakufaka kaka na molimo",
                french = "Les gens meurent seulement dans l'esprit",
                meaningFr = "La mémoire garde les défunts vivants",
                meaningLing = "Boyébi ya bato bakúfí ezali kobámbá bango na molílí"
            ),
            ProverbItem(
                lingala = "Soki osengeli kokoma, kotuna motuna",
                french = "Si tu dois écrire, pose une question",
                meaningFr = "La curiosité mène à la connaissance",
                meaningLing = "Kopɛ́ngi esáli na koyébi"
            ),
            ProverbItem(
                lingala = "Likambo ya kokamwa ezali te",
                french = "Il n'y a rien d'impossible",
                meaningFr = "Avec la volonté, tout est possible",
                meaningLing = "Na bolingó, nɔ́nsɔ ezali na nzela"
            ),
            ProverbItem(
                lingala = "Mbula ikunza, mvula ikunza",
                french = "La pluie tombe, l'eau coule",
                meaningFr = "Les choses suivent leur cours naturel",
                meaningLing = "Makambo mazali kobɛ́ngá na ndéngé na yango"
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
        modifier = modifier
            .fillMaxWidth()
            .kubaPulse(),   // Pulsation géométrique Kuba (Lokola Heritage)
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
                            themeColors.secondary.copy(alpha = if (themeColors.isDarkTheme) 0.1f else 0.15f),
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
                        text = stringResource(R.string.widget_proverb_title),
                        style = CongoTypography.KubaHeadline,
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
                
                // Traduction / Bolandi (selon la langue)
                Text(
                    text = todayProverb.french,
                    fontSize = 14.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = themeColors.textPrimary.copy(alpha = animatedAlpha),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // Signification (bilingue avancée)
                val meaningToShow = if (currentLanguage == "ling" && todayProverb.meaningLing.isNotEmpty()) 
                    todayProverb.meaningLing else todayProverb.meaningFr
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = themeColors.background.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${stringResource(R.string.widget_proverb_meaning)} $meaningToShow",
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
    val meaningFr: String,
    val meaningLing: String = ""
)
