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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.R
import com.muana.lokola.ui.theme.*
import com.muana.lokola.ui.theme.CongoTypography


/**
 * Données d'actualités RDC (simulées - à remplacer par une API réelle)
 */
data class NewsItem(
    val titleFr: String,
    val titleLing: String,
    val source: String,
    val categoryFr: String,
    val categoryLing: String,
    val url: String,
    val timeAgo: String
)

/**
 * Widget Actualités RDC - Flux d'informations locales
 */
@Composable
fun CongoNewsWidget(
    themeColors: ThemeColors,
    currentLanguage: String = "fr",
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    
    // Actualités simulées (à remplacer par une API comme Radio Okapi, Actualite.cd, etc.)
    val newsItems = remember {
        listOf(
            NewsItem(
                titleFr = "Kinshasa : Nouveau projet d'infrastructure sur le boulevard du 30 Juin",
                titleLing = "Kinshasa : Mosálá ya bopɛngi na boulevard du 30 Juin",
                source = "Radio Okapi",
                categoryFr = "Économie",
                categoryLing = "Ekonomí",
                url = "https://www.radiookapi.net",
                timeAgo = "2h"
            ),
            NewsItem(
                titleFr = "Culture : Festival de rumba congolaise annoncé pour décembre",
                titleLing = "Bonkóko : Fɛstiváli ya rumba ya Kôngɔ ebímí na décembre",
                source = "Actualite.cd",
                categoryFr = "Culture",
                categoryLing = "Bonkóko",
                url = "https://www.actualite.cd",
                timeAgo = "4h"
            ),
            NewsItem(
                titleFr = "Sport : Les Léopards se préparent pour les qualifications",
                titleLing = "Lisano : Bálɛpárdí bazali kobáti mpo na kɛsí",
                source = "Digital Congo",
                categoryFr = "Sport",
                categoryLing = "Lisano",
                url = "https://www.digitalcongo.net",
                timeAgo = "6h"
            ),
            NewsItem(
                titleFr = "Éducation : Réouverture des universités prévue la semaine prochaine",
                titleLing = "Boyébi : Kofúngola banté ya bapósɔ ebímí mpó ya mpɔ́sɔ",
                source = "ACP",
                categoryFr = "Éducation",
                categoryLing = "Boyébi",
                url = "https://www.acpcongo.com",
                timeAgo = "8h"
            )
        )
    }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .riverFlow(),   // Mouvement fluide style Fleuve (Lokola Heritage)
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
                            themeColors.gradientStart.copy(alpha = if (themeColors.isDarkTheme) 0.1f else 0.15f),
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
                        text = stringResource(R.string.widget_news_title),
                        style = CongoTypography.KubaHeadline,
                        color = themeColors.textPrimary
                    )
                }
                
                TextButton(onClick = {}) {
                    Text(stringResource(R.string.widget_news_see_all), fontSize = 12.sp)
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
                        currentLanguage = currentLanguage,
                        onClick = {
                            uriHandler.openUri(news.url)
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
    currentLanguage: String = "fr",
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
                    Text(if (currentLanguage == "ling") news.categoryLing else news.categoryFr, fontSize = 10.sp)
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
                text = if (currentLanguage == "ling") news.titleLing else news.titleFr,
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
