package com.muana.lokola.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.ui.theme.ThemeColors

/**
 * Données d'actualités RDC (simulées - à remplacer par une API réelle)
 */
data class NewsItem(
    val title: String,
    val source: String,
    val category: String,
    val url: String,
    val timeAgo: String
)

/**
 * Widget Actualités RDC - Flux d'informations locales
 */
@Composable
fun CongoNewsWidget(
    themeColors: ThemeColors,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    
    // Actualités simulées (à remplacer par une API comme Radio Okapi, Actualite.cd, etc.)
    val newsItems = remember {
        listOf(
            NewsItem(
                title = "Kinshasa : Nouveau projet d'infrastructure sur le boulevard du 30 Juin",
                source = "Radio Okapi",
                category = "Économie",
                url = "https://www.radiookapi.net",
                timeAgo = "2h"
            ),
            NewsItem(
                title = "Culture : Festival de rumba congolaise annoncé pour décembre",
                source = "Actualite.cd",
                category = "Culture",
                url = "https://www.actualite.cd",
                timeAgo = "4h"
            ),
            NewsItem(
                title = "Sport : Les Léopards se préparent pour les qualifications",
                source = "Digital Congo",
                category = "Sport",
                url = "https://www.digitalcongo.net",
                timeAgo = "6h"
            ),
            NewsItem(
                title = "Éducation : Réouverture des universités prévue la semaine prochaine",
                source = "ACP",
                category = "Éducation",
                url = "https://www.acpcongo.com",
                timeAgo = "8h"
            )
        )
    }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            themeColors.gradientStart.copy(alpha = 0.15f),
                            themeColors.surface
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📰",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Actualités RDC",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.textPrimary
                    )
                }
                
                TextButton(onClick = {}) {
                    Text("Voir tout", fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Liste horizontale des actualités
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(newsItems) { news ->
                    NewsCard(
                        news = news,
                        themeColors = themeColors,
                        onClick = {
                            try {
                                uriHandler.openUri(news.url)
                            } catch (e: Exception) {
                                // Gérer l'erreur silencieusement
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NewsCard(
    news: NewsItem,
    themeColors: ThemeColors,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.background.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Catégorie et temps
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Badge(
                    containerColor = themeColors.primary.copy(alpha = 0.2f),
                    contentColor = themeColors.primary
                ) {
                    Text(news.category, fontSize = 10.sp)
                }
                
                Text(
                    text = news.timeAgo,
                    fontSize = 10.sp,
                    color = themeColors.textSecondary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Titre
            Text(
                text = news.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = themeColors.textPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Source
            Text(
                text = news.source,
                fontSize = 11.sp,
                color = themeColors.textSecondary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
