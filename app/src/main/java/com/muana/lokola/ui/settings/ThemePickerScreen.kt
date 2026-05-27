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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.R
import com.muana.lokola.ui.theme.CongoTheme
import com.muana.lokola.ui.theme.CongoTypography
import com.muana.lokola.ui.theme.ThemeColors
import com.muana.lokola.ui.theme.getThemeColors
import com.muana.lokola.ui.theme.kubaPulse
import com.muana.lokola.util.ThemeManager
import kotlinx.coroutines.launch

@Composable
fun ThemePickerScreen(
    themeManager: ThemeManager,
    onBackClick: () -> Unit
) {
    val currentTheme by themeManager.currentTheme.collectAsState(initial = CongoTheme.FLEUVE)
    val scope = rememberCoroutineScope()
    
    val themeColors = getThemeColors(currentTheme)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.theme_picker_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←", fontSize = 24.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.primary,
                    titleContentColor = themeColors.textPrimary,
                    navigationIconContentColor = themeColors.textPrimary
                )
            )
        },
        containerColor = themeColors.background
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
                    text = stringResource(R.string.theme_picker_subtitle),
                    style = CongoTypography.KubaHeadline,
                    color = themeColors.textPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = stringResource(R.string.theme_picker_description),
                    style = CongoTypography.RumbaBodyLarge,
                    color = themeColors.textSecondary
                )
            }
            
            items(CongoTheme.values().toList()) { theme ->
                ThemeCard(
                    theme = theme,
                    isSelected = theme == currentTheme,
                    onClick = {
                        scope.launch {
                            themeManager.setTheme(theme)
                        }
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
            )
            .kubaPulse(),   // Animation culturelle (Lokola Heritage)
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
                    style = CongoTypography.KubaHeadline,
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
                        style = CongoTypography.RumbaBody,
                        color = colors.textPrimary
                    )
                    
                    Text(
                        text = getDescriptionForTheme(theme),
                        style = CongoTypography.NdomboloLabel,
                        color = colors.textSecondary
                    )
                }
                
                if (isSelected) {
                    Badge(
                        containerColor = colors.primary,
                        contentColor = colors.textPrimary
                    ) {
                        Text(stringResource(R.string.theme_active))
                    }
                }
            }
        }
    }
}

@Composable
fun getDescriptionForTheme(theme: CongoTheme): String {
    return when (theme) {
        CongoTheme.RUMBA -> stringResource(R.string.theme_rumba_desc)
        CongoTheme.SAVANE -> stringResource(R.string.theme_savane_desc)
        CongoTheme.FLEUVE -> stringResource(R.string.theme_fleuve_desc)
        CongoTheme.FORET -> stringResource(R.string.theme_foret_desc)
    }
}
