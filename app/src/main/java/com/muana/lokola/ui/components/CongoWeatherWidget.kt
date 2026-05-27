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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.R
import com.muana.lokola.ui.theme.*

/**
 * Widget Météo RDC - Avec paysages congolais emblématiques
 */
@Composable
fun CongoWeatherWidget(
    themeColors: ThemeColors,
    currentLanguage: String = "fr",
    modifier: Modifier = Modifier
) {
    // Données météo simulées pour Kinshasa (à remplacer par API réelle)
    val weatherData = remember {
        WeatherData(
            city = "Kinshasa",
            temperature = 28,
            conditionFr = "Ensoleillé",
            conditionLing = "Mɔtó",
            humidity = 65,
            windSpeed = 12,
            landscapeFr = "Fleuve Congo",
            landscapeLing = "Ebale Congo"
        )
    }

    val currentCondition = if (currentLanguage == "ling") weatherData.conditionLing else weatherData.conditionFr
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .riverFlow(),   // Flux fluide style Fleuve (Lokola Heritage)
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
                        colors = when (currentCondition) {
                            "Ensoleillé", "Mɔtó" -> listOf(
                                themeColors.secondary.copy(alpha = if (themeColors.isDarkTheme) 0.25f else 0.4f),
                                themeColors.surface
                            )
                            "Nuageux", "Mapú" -> listOf(
                                themeColors.textSecondary.copy(alpha = if (themeColors.isDarkTheme) 0.2f else 0.3f),
                                themeColors.surface
                            )
                            "Pluie", "Mbúla" -> listOf(
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
                        text = getWeatherEmoji(if (currentLanguage == "ling") weatherData.conditionLing else weatherData.conditionFr),
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
                        text = if (currentLanguage == "ling") weatherData.conditionLing else weatherData.conditionFr,
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
                                text = "🏞️ ${if (currentLanguage == "ling") weatherData.landscapeLing else weatherData.landscapeFr}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = themeColors.primary
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "💧 ${stringResource(R.string.widget_weather_humidity)} ${weatherData.humidity}%",
                                    fontSize = 11.sp,
                                    color = themeColors.textSecondary
                                )
                                
                                Text(
                                    text = "💨 ${stringResource(R.string.widget_weather_wind)} ${weatherData.windSpeed} km/h",
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
        // French
        "Ensoleillé", "Mɔtó" -> "☀️"
        "Nuageux", "Mapú" -> "☁️"
        "Pluie", "Mbúla" -> "🌧️"
        "Orage", "Nkúmba" -> "⛈️"
        "Partiellement nuageux" -> "⛅"
        else -> "🌤️"
    }
}

data class WeatherData(
    val city: String,
    val temperature: Int,
    val conditionFr: String,
    val conditionLing: String,
    val humidity: Int,
    val windSpeed: Int,
    val landscapeFr: String,
    val landscapeLing: String
)
