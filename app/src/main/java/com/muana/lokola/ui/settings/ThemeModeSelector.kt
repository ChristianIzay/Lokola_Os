package com.muana.lokola.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.R
import com.muana.lokola.ui.theme.*
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
                text = stringResource(R.string.theme_mode_title),
                style = CongoTypography.KubaHeadline,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = stringResource(R.string.theme_mode_title),
                style = CongoTypography.KubaHeadline,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Groupe de sélection
            Column(
                modifier = Modifier.selectableGroup()
            ) {
                // Option Mode Clair
                ThemeModeOption(
                    title = stringResource(R.string.theme_mode_light),
                    description = stringResource(R.string.theme_mode_light_desc),
                    icon = "☀️",
                    selected = currentThemeMode == ThemeMode.LIGHT,
                    onClick = {
                        // Dans un vrai environnement, on utiliserait coroutineScope
                        // Pour l'instant, c'est juste visuel
                    },
                    modifier = Modifier.ndomboloBounce(pressed = currentThemeMode == ThemeMode.LIGHT)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Option Mode Sombre
                ThemeModeOption(
                    title = stringResource(R.string.theme_mode_dark),
                    description = stringResource(R.string.theme_mode_dark_desc),
                    icon = "🌙",
                    selected = currentThemeMode == ThemeMode.DARK,
                    onClick = {
                        // Dans un vrai environnement, on utiliserait coroutineScope
                    },
                    modifier = Modifier.ndomboloBounce(currentThemeMode == ThemeMode.DARK)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Option Système
                ThemeModeOption(
                    title = stringResource(R.string.theme_mode_system),
                    description = stringResource(R.string.theme_mode_system_desc),
                    icon = "⚙️",
                    selected = currentThemeMode == ThemeMode.SYSTEM,
                    onClick = {
                        // Dans un vrai environnement, on utiliserait coroutineScope
                    },
                    modifier = Modifier.ndomboloBounce(currentThemeMode == ThemeMode.SYSTEM)
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
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
                text = description,
                style = CongoTypography.NdomboloLabel,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        
        // Texte
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = if (selected) CongoTypography.RumbaBody else CongoTypography.NdomboloLabel,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = stringResource(R.string.theme_mode_subtitle),
                style = CongoTypography.NdomboloLabel,
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
