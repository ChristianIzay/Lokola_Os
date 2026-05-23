package com.muana.lokola.ui.components

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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Événements culturels congolais importants
 */
data class CulturalEvent(
    val date: LocalDate,
    val title: String,
    val description: String,
    val type: EventType
)

enum class EventType {
    INDEPENDENCE,
    CULTURAL,
    RELIGIOUS,
    NATIONAL
}

/**
 * Widget Calendrier Culturel - Dates importantes de la RDC
 */
@Composable
fun CulturalCalendarWidget(
    themeColors: ThemeColors,
    modifier: Modifier = Modifier
) {
    val currentDate = remember { LocalDate.now() }
    val formattedDate = remember {
        currentDate.format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH))
    }
    
    // Événements culturels congolais (exemples)
    val culturalEvents = remember {
        listOf(
            CulturalEvent(
                date = LocalDate.of(currentDate.year, 6, 30),
                title = "🇨🇩 Jour de l'Indépendance",
                description = "Fête nationale - 30 Juin 1960",
                type = EventType.INDEPENDENCE
            ),
            CulturalEvent(
                date = LocalDate.of(currentDate.year, 1, 4),
                title = "Jour des Martyrs",
                description = "Commémoration des martyrs de l'indépendance",
                type = EventType.NATIONAL
            ),
            CulturalEvent(
                date = LocalDate.of(currentDate.year, 5, 1),
                title = "Fête du Travail",
                description = "Journée internationale des travailleurs",
                type = EventType.NATIONAL
            ),
            CulturalEvent(
                date = LocalDate.of(currentDate.year, 8, 1),
                title = "Fête des Parents",
                description = "Célébration familiale congolaise",
                type = EventType.CULTURAL
            )
        ).filter { it.date.month == currentDate.month || 
                   it.date.month == currentDate.plusMonths(1).month }
         .sortedBy { it.date }
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
                    Brush.linearGradient(
                        colors = listOf(
                            themeColors.gradientEnd.copy(alpha = if (themeColors.isDarkTheme) 0.1f else 0.2f),
                            themeColors.surface
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Header avec date actuelle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📅",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Calendrier Culturel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.textPrimary
                        )
                        Text(
                            text = formattedDate.capitalize(),
                            fontSize = 12.sp,
                            color = themeColors.textSecondary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Événements à venir
            if (culturalEvents.isEmpty()) {
                Text(
                    text = "Aucun événement ce mois-ci",
                    fontSize = 14.sp,
                    color = themeColors.textSecondary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                culturalEvents.take(3).forEach { event ->
                    EventItem(
                        event = event,
                        themeColors = themeColors,
                        isToday = event.date == currentDate
                    )
                    if (event != culturalEvents.take(3).last()) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun EventItem(
    event: CulturalEvent,
    themeColors: ThemeColors,
    isToday: Boolean
) {
    val dayFormatter = DateTimeFormatter.ofPattern("d")
    val monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.FRENCH)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isToday) 
                    themeColors.primary.copy(alpha = 0.15f)
                else 
                    themeColors.background.copy(alpha = 0.3f)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Date box
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isToday) themeColors.primary else themeColors.secondary
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = event.date.format(dayFormatter),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isToday) themeColors.textPrimary else themeColors.textPrimary
                )
                Text(
                    text = event.date.format(monthFormatter).capitalize(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isToday) themeColors.textPrimary else themeColors.textPrimary
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Event details
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = themeColors.textPrimary
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = event.description,
                fontSize = 12.sp,
                color = themeColors.textSecondary
            )
        }
        
        // Badge si c'est aujourd'hui
        if (isToday) {
            Badge(
                containerColor = themeColors.primary,
                contentColor = themeColors.textPrimary
            ) {
                Text("Aujourd'hui", fontSize = 9.sp)
            }
        }
    }
}
