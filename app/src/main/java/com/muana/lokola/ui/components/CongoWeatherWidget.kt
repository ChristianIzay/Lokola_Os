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

/**
 * Widget Météo RDC - Avec paysages congolais emblématiques
 */
@Composable
fun CongoWeatherWidget(
    themeColors: ThemeColors,
    modifier: Modifier = Modifier
) {
    // Données météo simulées pour Kinshasa (à remplacer par API réelle)
    val weatherData = remember {
        WeatherData(
            city = "Kinshasa",
            temperature = 28,
            condition = "Ensoleillé",
            humidity = 65,
            windSpeed = 12,
            landscape = "Fleuve Congo"
        )
    }
    
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
                .height(160.dp)
                .background(
                    Brush.verticalGradient(
                        colors = when (weatherData.condition) {
                            "Ensoleillé" -> listOf(
                                themeColors.secondary.copy(alpha = if (themeColors.isDarkTheme) 0.25f else 0.4f),
                                themeColors.surface
                            )
                            "Nuageux" -> listOf(
                                themeColors.textSecondary.copy(alpha = if (themeColors.isDarkTheme) 0.2f else 0.3f),
                                themeColors.surface
                            )
                            "Pluie" -> listOf(
                                themeColors.primary.copy(alpha = if (themeColors.isDarkTheme) 0.2f else 0.3f),
                                themeColors.surface
                            )
                            else -> listOf(
                                themeColors.gradientStart.copy(alpha = if (themeColors.isDarkTheme) 0.2f else 0.3f),
                                themeColors.surface
                            )
                        }
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icône et température
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = getWeatherEmoji(weatherData.condition),
                        fontSize = 48.sp
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "${weatherData.temperature}°C",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.textPrimary
                    )
                    
                    Text(
                        text = weatherData.condition,
                        fontSize = 14.sp,
                        color = themeColors.textSecondary
                    )
                }
                
                // Détails et paysage
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    // Ville
                    Text(
                        text = "📍 ${weatherData.city}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = themeColors.textPrimary
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Paysage congolais
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = themeColors.background.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "🏞️ ${weatherData.landscape}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = themeColors.primary
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "💧 ${weatherData.humidity}%",
                                    fontSize = 11.sp,
                                    color = themeColors.textSecondary
                                )
                                
                                Text(
                                    text = "💨 ${weatherData.windSpeed} km/h",
                                    fontSize = 11.sp,
                                    color = themeColors.textSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getWeatherEmoji(condition: String): String {
    return when (condition) {
        "Ensoleillé" -> "☀️"
        "Nuageux" -> "☁️"
        "Pluie" -> "🌧️"
        "Orage" -> "⛈️"
        "Partiellement nuageux" -> "⛅"
        else -> "🌤️"
    }
}

data class WeatherData(
    val city: String,
    val temperature: Int,
    val condition: String,
    val humidity: Int,
    val windSpeed: Int,
    val landscape: String
)
