package com.muana.lokola.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.ui.theme.CongoTheme
import com.muana.lokola.ui.theme.ThemeColors
import com.muana.lokola.ui.theme.getThemeColors
import com.muana.lokola.util.ThemeManager

@Composable
fun ThemePickerScreen(
    themeManager: ThemeManager,
    onBackClick: () -> Unit
) {
    val currentTheme by themeManager.currentTheme.collectAsState(initial = CongoTheme.FLEUVE)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thème Culturel") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←", fontSize = 24.sp)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Choisissez l'ambiance de Lokola OS",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Chaque thème est inspiré de la richesse culturelle congolaise",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            items(CongoTheme.values().toList()) { theme ->
                ThemeCard(
                    theme = theme,
                    isSelected = theme == currentTheme,
                    onClick = {
                        // Dans un vrai environnement, on utiliserait coroutineScope
                        // Pour l'instant, c'est juste visuel
                    }
                )
            }
        }
    }
}

@Composable
fun ThemeCard(
    theme: CongoTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = getThemeColors(theme)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) colors.primary else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Preview du thème
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(colors.gradientStart, colors.gradientEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${theme.icon} ${theme.displayName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = theme.displayName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = getDescriptionForTheme(theme),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                if (isSelected) {
                    Badge(
                        containerColor = colors.primary,
                        contentColor = colors.textPrimary
                    ) {
                        Text("Actif")
                    }
                }
            }
        }
    }
}

fun getDescriptionForTheme(theme: CongoTheme): String {
    return when (theme) {
        CongoTheme.RUMBA -> "Énergie et passion de la rumba congolaise"
        CongoTheme.SAVANE -> "Chaleurs dorées des paysages congolais"
        CongoTheme.FLEUVE -> "Majesté du fleuve Congo"
        CongoTheme.FORET -> "Verdure luxuriante de la forêt tropicale"
    }
}
