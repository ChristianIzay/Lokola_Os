package com.muana.lokola.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muana.lokola.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onWallpaperClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val dataSaverEnabled by viewModel.dataSaverEnabled.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.common_back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Data Saver Setting
            SettingsSection(
                title = stringResource(R.string.settings_datasaver)
            ) {
                SettingsToggleItem(
                    icon = Icons.Default.DataUsage,
                    title = stringResource(R.string.settings_datasaver),
                    description = stringResource(R.string.settings_datasaver_description),
                    checked = dataSaverEnabled,
                    onCheckedChange = { viewModel.toggleDataSaver(it) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Language Setting
            SettingsSection(
                title = stringResource(R.string.settings_language)
            ) {
                SettingsItem(
                    icon = Icons.Default.Language,
                    title = stringResource(R.string.settings_language),
                    description = if (currentLanguage == "fr")
                        stringResource(R.string.onboarding_language_french)
                    else
                        stringResource(R.string.onboarding_language_lingala),
                    onClick = {
                        // Toggle language
                        val newLanguage = if (currentLanguage == "fr") "ling" else "fr"
                        viewModel.changeLanguage(newLanguage)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Wallpaper Setting
            SettingsSection(
                title = "Personnalisation"
            ) {
                SettingsItem(
                    icon = Icons.Default.Image,
                    title = "Fond d'écran",
                    description = "Changer l'arrière-plan du launcher",
                    onClick = onWallpaperClick
                )
                
                SettingsItem(
                    icon = Icons.Default.ColorLens,
                    title = "Thème Culturel",
                    description = "Rumba, Savane, Fleuve ou Forêt",
                    onClick = onThemeClick
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // About Section
            SettingsSection(
                title = stringResource(R.string.settings_about)
            ) {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = stringResource(R.string.settings_version),
                    description = "1.0.0",
                    onClick = {}
                )

                SettingsItem(
                    icon = Icons.Default.Favorite,
                    title = stringResource(R.string.settings_made_in),
                    description = "Muana-Tech © 2026",
                    onClick = {}
                )

                SettingsItem(
                    icon = Icons.Default.Chat,
                    title = stringResource(R.string.settings_contact),
                    description = "+243 XXX XXX XXX",
                    onClick = {
                        // Open WhatsApp (placeholder URL)
                        try {
                            uriHandler.openUri("https://wa.me/243XXXXXXXXX")
                        } catch (e: Exception) {
                            // Handle error
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            Text(
                text = "Lokola OS - Fierté Congolaise",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            content()
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            modifier = Modifier.size(20.dp)
        )
    }

    Divider(
        modifier = Modifier.padding(start = 56.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
}

@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}
