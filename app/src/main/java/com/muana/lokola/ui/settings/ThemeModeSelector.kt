package com.muana.lokola.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.util.ThemeMode
import com.muana.lokola.util.ThemeModeManager

@Composable
fun ThemeModeSelector(
    themeModeManager: ThemeModeManager,
    modifier: Modifier = Modifier
) {
    val currentThemeMode by themeModeManager.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Mode d'affichage",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Choisissez comment Lokola OS s'adapte à votre environnement",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Groupe de sélection
            Column(
                modifier = Modifier.selectableGroup()
            ) {
                // Option Mode Clair
                ThemeModeOption(
                    title = "Mode Clair",
                    description = "Toujours utiliser le thème clair",
                    icon = "☀️",
                    selected = currentThemeMode == ThemeMode.LIGHT,
                    onClick = {
                        // Dans un vrai environnement, on utiliserait coroutineScope
                        // Pour l'instant, c'est juste visuel
                    }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Option Mode Sombre
                ThemeModeOption(
                    title = "Mode Sombre",
                    description = "Toujours utiliser le thème sombre",
                    icon = "🌙",
                    selected = currentThemeMode == ThemeMode.DARK,
                    onClick = {
                        // Dans un vrai environnement, on utiliserait coroutineScope
                    }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Option Système
                ThemeModeOption(
                    title = "Système",
                    description = "Suivre les paramètres du téléphone",
                    icon = "⚙️",
                    selected = currentThemeMode == ThemeMode.SYSTEM,
                    onClick = {
                        // Dans un vrai environnement, on utiliserait coroutineScope
                    }
                )
            }
        }
    }
}

@Composable
fun ThemeModeOption(
    title: String,
    description: String,
    icon: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icône
        Text(
            text = icon,
            fontSize = 24.sp,
            modifier = Modifier.padding(end = 12.dp)
        )
        
        // Texte
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        
        // Bouton radio
        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}
