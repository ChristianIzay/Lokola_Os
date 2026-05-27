package com.muana.lokola.ui.launcher

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.muana.lokola.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.muana.lokola.ui.components.DataSaverWidget
import com.muana.lokola.ui.components.LanguageFAB
import com.muana.lokola.ui.components.RumbaWidget
import com.muana.lokola.ui.components.CongoNewsWidget
import com.muana.lokola.ui.components.CulturalCalendarWidget
import com.muana.lokola.ui.components.ProverbWidget
import com.muana.lokola.ui.components.CongoWeatherWidget
import com.muana.lokola.ui.theme.drawCulturalPattern

import com.muana.lokola.ui.theme.*
import com.muana.lokola.ui.theme.CongoTypography
import com.muana.lokola.util.AppLauncher
import com.muana.lokola.util.SettingsButtonPosition
import com.muana.lokola.util.ThemeManager
import com.muana.lokola.util.WallpaperManager
import com.muana.lokola.util.WidgetPreferencesManager
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class AppItem(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val route: String? = null
)

data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val gradient: List<Color>
)

@Composable
fun LauncherScreen(
    wallpaperManager: WallpaperManager,
    themeManager: ThemeManager,
    widgetPreferencesManager: WidgetPreferencesManager,
    dataSaverEnabled: Boolean,
    onDataSaverToggle: (Boolean) -> Unit,
    onMayebiClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onThemeClick: () -> Unit = {},
    currentLanguage: String = "fr",
    onLanguageChange: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val installedApps = remember { AppLauncher.getInstalledApps(context) }

    val selectedWallpaperId by wallpaperManager.selectedWallpaperId.collectAsState(initial = 0)
    val currentTheme by themeManager.currentTheme.collectAsState(initial = CongoTheme.FLEUVE)
    val themeColors = getThemeColors(currentTheme)
    
    // États des widgets
    val rumbaEnabled by widgetPreferencesManager.isRumbaWidgetEnabled.collectAsState(initial = true)
    val newsEnabled by widgetPreferencesManager.isNewsWidgetEnabled.collectAsState(initial = true)
    val calendarEnabled by widgetPreferencesManager.isCalendarWidgetEnabled.collectAsState(initial = true)
    val proverbEnabled by widgetPreferencesManager.isProverbWidgetEnabled.collectAsState(initial = false)
    val weatherEnabled by widgetPreferencesManager.isWeatherWidgetEnabled.collectAsState(initial = false)
    
    // Position du bouton Paramètres (Phase B)
    val settingsButtonPosition by widgetPreferencesManager.settingsButtonPosition.collectAsState(initial = SettingsButtonPosition.BOTTOM_RIGHT)
    
    // État pour le Bottom Sheet Paramètres (Phase B)
    var showLauncherSettings by remember { mutableStateOf(false) }
    
    val currentDate = remember { LocalDate.now() }
    val formattedDate = remember {
        currentDate.format(DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRENCH))
    }

    val pagerState = rememberPagerState(pageCount = { 3 })
    
    // État pour la position du bouton draggable (persisté)
    var fabOffsetX by remember { mutableStateOf(0f) }
    var fabOffsetY by remember { mutableStateOf(0f) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp.value
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp.value
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Background avec pattern culturel ou image (derrière tout)
        if (selectedWallpaperId > 0) {
            val wallpapers = wallpaperManager.getAvailableWallpapers()
            val resId = wallpapers.getOrNull(selectedWallpaperId - 1)
            if (resId != null) {
                val inputStream = context.resources.openRawResource(resId)
                val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(color = themeColors.background)
                drawCulturalPattern(size, currentTheme, themeColors)
            }
        }

        // === 3 PAGES AVEC SWIPE ===
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> LauncherPageWidgets(
                    formattedDate = formattedDate,
                    themeColors = themeColors,
                    currentLanguage = currentLanguage,
                    rumbaEnabled = rumbaEnabled,
                    newsEnabled = newsEnabled,
                    calendarEnabled = calendarEnabled,
                    proverbEnabled = proverbEnabled,
                    weatherEnabled = weatherEnabled
                )
                1 -> LauncherPageApps(
                    context = context,
                    installedApps = installedApps,
                    themeColors = themeColors
                )
                2 -> LauncherPageEmpty(themeColors = themeColors)
            }
        }

        // === Indicateur moderne (barre + animation fluide) ===
        PageIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        )

        // === DOCK FIXE (toujours visible en bas) ===
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            CongoDockBar(context = context, themeColors = themeColors)
        }

        // Bouton Paramètres DRAGGABLE
        DraggableSettingsButton(
            onClick = { showLauncherSettings = true },
            themeColors = themeColors,
            initialOffsetX = fabOffsetX,
            initialOffsetY = fabOffsetY,
            onOffsetChange = { x, y ->
                fabOffsetX = x
                fabOffsetY = y
            },
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )

        // Bottom Sheet Paramètres (Phase B)
        if (showLauncherSettings) {
            LauncherSettingsSheet(
                widgetPreferencesManager = widgetPreferencesManager,
                themeManager = themeManager,
                themeColors = themeColors,
                currentLanguage = currentLanguage,
                onLanguageChange = onLanguageChange,
                dataSaverEnabled = dataSaverEnabled,
                onDataSaverToggle = onDataSaverToggle,
                onThemeClick = onThemeClick,
                onDismiss = { showLauncherSettings = false }
            )
        }
    }
}

// ==================== LES 3 PAGES DU LAUNCHER ====================

@Composable
fun LauncherPageWidgets(
    formattedDate: String,
    themeColors: ThemeColors,
    currentLanguage: String = "fr",
    rumbaEnabled: Boolean,
    newsEnabled: Boolean,
    calendarEnabled: Boolean,
    proverbEnabled: Boolean,
    weatherEnabled: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 72.dp)   // Espace pour le dock fixe
    ) {
        // Header fixe (ne scroll pas)
        HeaderSection(formattedDate = formattedDate, themeColors = themeColors)

        Spacer(modifier = Modifier.height(4.dp))

        // Sous-titre léger (fixe)
        Text(
            text = stringResource(R.string.launcher_cultural_content),
            fontSize = 13.sp,
            color = themeColors.textSecondary,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        // Zone des widgets scrollable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            CulturalWidgetsSection(
                themeColors = themeColors,
                currentLanguage = currentLanguage,
                rumbaEnabled = rumbaEnabled,
                newsEnabled = newsEnabled,
                calendarEnabled = calendarEnabled,
                proverbEnabled = proverbEnabled,
                weatherEnabled = weatherEnabled
            )

            // Espace en bas pour bien respirer après le dernier widget
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun LauncherPageApps(
    context: Context,
    installedApps: List<AppLauncher.AppInfo>,
    themeColors: ThemeColors
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 72.dp)
    ) {
        // Header amélioré pour la page Applications
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.launcher_applications_title),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = themeColors.textPrimary
            )
            Text(
                text = stringResource(R.string.launcher_applications_count, installedApps.size),
                fontSize = 14.sp,
                color = themeColors.textSecondary
            )
        }

        // Ligne de séparation subtile
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 20.dp)
                .background(themeColors.textSecondary.copy(alpha = 0.15f))
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(installedApps) { app ->
                InstalledAppIcon(
                    app = app,
                    onClick = {
                        AppLauncher.launchAppFromInfo(context, app)
                    },
                    themeColors = themeColors
                )
            }
        }
    }
}

@Composable
fun LauncherPageEmpty(themeColors: ThemeColors) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 72.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Text(
                text = "＋",
                fontSize = 64.sp,
                color = themeColors.textSecondary.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.launcher_page3_title),
                fontSize = 20.sp,
                color = themeColors.textPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.launcher_page3_description),
                fontSize = 14.sp,
                color = themeColors.textSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PageIndicator(
    pagerState: androidx.compose.foundation.pager.PagerState,
    modifier: Modifier = Modifier
) {
    val density = androidx.compose.ui.platform.LocalDensity.current

    val pageCount = 3
    val pillWidth = 26.dp
    val pillHeight = 4.dp
    val spacing = 10.dp

    val currentPage = pagerState.currentPage
    val offsetFraction = pagerState.currentPageOffsetFraction

    // Position fluide du pill (0.0 → 2.0)
    val position = currentPage + offsetFraction

    // Calculs en pixels pour l'offset dynamique
    val pillWidthPx = with(density) { pillWidth.toPx() }
    val spacingPx = with(density) { spacing.toPx() }
    val totalWidthPx = (pillWidthPx * pageCount) + (spacingPx * (pageCount - 1))

    // Position X du coin gauche du pill actif (en pixels)
    val pillOffsetX = position * (pillWidthPx + spacingPx)

    // On centre l'indicateur globalement
    val centeredOffset = pillOffsetX - (totalWidthPx / 2) + (pillWidthPx / 2)

    Box(
        modifier = modifier
            .width(with(density) { totalWidthPx.toDp() })
            .height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        // Piste de fond
        Box(
            modifier = Modifier
                .width(with(density) { totalWidthPx.toDp() })
                .height(pillHeight)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.22f))
        )

        // Pill animé qui glisse (offset en pixels pour un suivi fluide pendant le swipe)
        Box(
            modifier = Modifier
                .offset { androidx.compose.ui.unit.IntOffset(centeredOffset.toInt(), 0) }
                .width(pillWidth)
                .height(pillHeight)
                .clip(RoundedCornerShape(50))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.9f)
                        )
                    )
                )
        )
    }
}

@Composable
fun HeaderSection(formattedDate: String, themeColors: ThemeColors) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .statusBarsPadding()
            .rumbaGroove(),   // Animation rythmique inspirée de la rumba (Lokola Heritage)
        colors = CardDefaults.cardColors(
            containerColor = themeColors.primary
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(themeColors.primary, themeColors.primaryVariant)
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formattedDate.capitalize(),
                        style = CongoTypography.NdomboloLabel,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = stringResource(R.string.launcher_header_greeting),
                    style = CongoTypography.KubaDisplayLarge,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = stringResource(R.string.launcher_header_subtitle),
                    style = CongoTypography.RumbaBody,
                    color = themeColors.secondary
                )
            }
        }
    }
}

@Composable
fun QuickActionsRow() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(getQuickActions()) { action ->
            QuickActionCard(action = action)
        }
    }
}

@Composable
fun QuickActionCard(action: QuickAction) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp)
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = action.gradient
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Icon(
                    imageVector = action.icon,
                    contentDescription = action.title,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = action.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun InstalledAppIcon(
    app: AppLauncher.AppInfo,
    onClick: () -> Unit,
    themeColors: ThemeColors
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.2f))
                .shadow(4.dp, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = app.icon.toBitmap().asImageBitmap(),
                contentDescription = app.appName,
                modifier = Modifier.size(40.dp)
            )
        }
        
        Text(
            text = app.appName,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = themeColors.textPrimary,
            modifier = Modifier
                .padding(top = 6.dp)
                .width(64.dp),
            maxLines = 2
        )
    }
}

@Composable
fun CongoAppIcon(
    app: AppItem,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(app.backgroundColor)
                .shadow(4.dp, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = app.icon,
                contentDescription = app.name,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
        
        Text(
            text = app.name,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = LauncherTextPrimary,
            modifier = Modifier
                .padding(top = 6.dp)
                .width(64.dp),
            maxLines = 2
        )
    }
}

@Composable
fun CongoDockBar(context: Context, themeColors: ThemeColors) {
    // Dock très transparent (style verre) + fine bordure en haut
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        themeColors.dockBackground.copy(alpha = 0.65f), // Plus transparent
                        themeColors.dockBackground.copy(alpha = 0.25f)
                    )
                )
            )
    ) {
        // Fine ligne supérieure pour améliorer la lisibilité
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(themeColors.textPrimary.copy(alpha = 0.12f))
                .align(Alignment.TopCenter)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icônes habituelles (standard Android)
            DockItem(Icons.Default.Phone, stringResource(R.string.dock_phone), themeColors.primary, themeColors) {
                AppLauncher.launchDialer(context)
            }
            DockItem(Icons.Default.Message, stringResource(R.string.dock_messages), themeColors.accent, themeColors) {
                AppLauncher.launchMessages(context)
            }
            DockItem(Icons.Default.Public, stringResource(R.string.dock_internet), themeColors.primaryVariant, themeColors) {
                AppLauncher.launchBrowser(context)
            }
            DockItem(Icons.Default.PhotoCamera, stringResource(R.string.dock_photo), themeColors.secondary, themeColors) {
                AppLauncher.launchCamera(context)
            }
        }
    }
}

@Composable
fun DockItem(icon: ImageVector, label: String, color: Color, themeColors: ThemeColors, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color)
                .kubaPulse(),   // Pulsation géométrique style Kuba (Lokola Heritage)
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
        Text(
            text = label,
            style = CongoTypography.NdomboloLabel,
            color = themeColors.textSecondary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

fun getCongoAppList(): List<AppItem> {
    return listOf(
        AppItem(1, "Mayebi", Icons.Default.School, Color(0xFF007FFF), "mayebi"),
        AppItem(2, "Téléphone", Icons.Default.Phone, Color(0xFF4CAF50), "phone"),
        AppItem(3, "Messages", Icons.Default.Message, Color(0xFF2196F3), "messages"),
        AppItem(4, "Internet", Icons.Default.Public, Color(0xFF003F87), "browser"),
        AppItem(5, "Photos", Icons.Default.Image, Color(0xFFFF6F00), "gallery"),
        AppItem(6, "Musique", Icons.Default.MusicNote, Color(0xFFE91E63), "music"),
        AppItem(7, "Vidéos", Icons.Default.VideoLibrary, Color(0xFF9C27B0), "videos"),
        AppItem(8, "Fichiers", Icons.Default.Folder, Color(0xFFFF8F00), "files"),
        AppItem(9, "Calcul", Icons.Default.Calculate, Color(0xFF00897B), "calculator"),
        AppItem(10, "Calendrier", Icons.Default.CalendarToday, Color(0xFF5E35B1), "calendar"),
        AppItem(11, "Horloge", Icons.Default.AccessTime, Color(0xFF6D4C41), "clock"),
        AppItem(12, "Paramètres", Icons.Default.Settings, Color(0xFF546E7A), "settings")
    )
}

fun getQuickActions(): List<QuickAction> {
    return listOf(
        QuickAction(
            title = "Rumba Congolaise",
            icon = Icons.Default.MusicNote,
            gradient = listOf(Color(0xFFE91E63), Color(0xFFFF5722))
        ),
        QuickAction(
            title = "Actualités RDC",
            icon = Icons.Default.Article,
            gradient = listOf(Color(0xFF007FFF), Color(0xFF003F87))
        )
    )
}

@Composable
fun SearchBarSection(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        TextField(
            value = query,
            onValueChange = { 
                query = it
                onSearch(it)
            },
            placeholder = { 
                Text(
                    "Rechercher une application...",
                    color = Color.White.copy(alpha = 0.7f)
                ) 
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White.copy(alpha = 0.9f)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            keyboardOptions = KeyboardOptions.Default,
            singleLine = true
        )
    }
}

@Composable
fun CulturalWidgetsSection(
    themeColors: ThemeColors,
    currentLanguage: String = "fr",
    rumbaEnabled: Boolean = true,
    newsEnabled: Boolean = true,
    calendarEnabled: Boolean = true,
    proverbEnabled: Boolean = false,
    weatherEnabled: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Widget Rumba UNESCO
        if (rumbaEnabled) {
            RumbaWidget(themeColors = themeColors, currentLanguage = currentLanguage)
        }
        
        // Widget Actualités RDC
        if (newsEnabled) {
            CongoNewsWidget(themeColors = themeColors, currentLanguage = currentLanguage)
        }
        
        // Widget Calendrier Culturel
        if (calendarEnabled) {
            CulturalCalendarWidget(themeColors = themeColors, currentLanguage = currentLanguage)
        }
        
        // Widget Proverbes Congolais
        if (proverbEnabled) {
            ProverbWidget(themeColors = themeColors, currentLanguage = currentLanguage)
        }
        
        // Widget Météo RDC
        if (weatherEnabled) {
            CongoWeatherWidget(themeColors = themeColors, currentLanguage = currentLanguage)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LauncherSettingsSheet(
    widgetPreferencesManager: WidgetPreferencesManager,
    themeManager: ThemeManager,
    themeColors: ThemeColors,
    currentLanguage: String,
    onLanguageChange: (String) -> Unit,
    dataSaverEnabled: Boolean,
    onDataSaverToggle: (Boolean) -> Unit,
    onThemeClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = themeColors.surface,
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.launcher_settings),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.textPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = themeColors.textPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // === Section Widgets ===
            SectionHeader(
                icon = Icons.Default.Widgets,
                title = stringResource(R.string.settings_sheet_widgets),
                themeColors = themeColors
            )

            val rumbaEnabled by widgetPreferencesManager.isRumbaWidgetEnabled.collectAsState(initial = true)
            val newsEnabled by widgetPreferencesManager.isNewsWidgetEnabled.collectAsState(initial = true)
            val calendarEnabled by widgetPreferencesManager.isCalendarWidgetEnabled.collectAsState(initial = true)
            val proverbEnabled by widgetPreferencesManager.isProverbWidgetEnabled.collectAsState(initial = false)
            val weatherEnabled by widgetPreferencesManager.isWeatherWidgetEnabled.collectAsState(initial = false)

            SwitchRow("Rumba Congolaise", rumbaEnabled, themeColors) { scope.launch { widgetPreferencesManager.setRumbaWidgetEnabled(it) } }
            SwitchRow("Actualités RDC", newsEnabled, themeColors) { scope.launch { widgetPreferencesManager.setNewsWidgetEnabled(it) } }
            SwitchRow("Calendrier Culturel", calendarEnabled, themeColors) { scope.launch { widgetPreferencesManager.setCalendarWidgetEnabled(it) } }
            SwitchRow("Proverbes Congolais", proverbEnabled, themeColors) { scope.launch { widgetPreferencesManager.setProverbWidgetEnabled(it) } }
            SwitchRow("Météo RDC", weatherEnabled, themeColors) { scope.launch { widgetPreferencesManager.setWeatherWidgetEnabled(it) } }

            Spacer(modifier = Modifier.height(28.dp))

            // === Section Langue ===
            SectionHeader(
                icon = Icons.Default.Language,
                title = stringResource(R.string.settings_sheet_language),
                themeColors = themeColors
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LanguageButton(stringResource(R.string.onboarding_language_french), currentLanguage == "fr", themeColors) { onLanguageChange("fr") }
                LanguageButton(stringResource(R.string.onboarding_language_lingala), currentLanguage == "ling", themeColors) { onLanguageChange("ling") }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // === Section Data Saver ===
            SectionHeader(
                icon = Icons.Default.DataUsage,
                title = stringResource(R.string.settings_sheet_data_saver),
                themeColors = themeColors
            )

            SwitchRow("Économiser les données", dataSaverEnabled, themeColors, onDataSaverToggle)

            Spacer(modifier = Modifier.height(28.dp))

            // === Section Thème Culturel ===
            SectionHeader(
                icon = Icons.Default.ColorLens,
                title = "Thème Culturel",
                themeColors = themeColors
            )
            
            val currentTheme by themeManager.currentTheme.collectAsState(initial = CongoTheme.FLEUVE)
            
            ThemeSelectorItem(
                currentTheme = currentTheme,
                themeColors = themeColors,
                onClick = {
                    onDismiss()
                    onThemeClick()
                }
            )

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun SectionHeader(
    icon: ImageVector,
    title: String,
    themeColors: ThemeColors
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = themeColors.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = themeColors.primary
        )
    }
}

@Composable
private fun SwitchRow(
    label: String,
    checked: Boolean,
    themeColors: ThemeColors,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = themeColors.textPrimary,
            fontSize = 14.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = themeColors.primary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = themeColors.textSecondary.copy(alpha = 0.35f)
            )
        )
    }
}

@Composable
private fun LanguageButton(
    text: String,
    selected: Boolean,
    themeColors: ThemeColors,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) themeColors.primary else themeColors.surface,
            contentColor = if (selected) Color.White else themeColors.textPrimary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(42.dp)
    ) {
        Text(text, fontSize = 14.sp)
    }
}

@Composable
private fun PositionSelector(
    currentPosition: SettingsButtonPosition,
    onPositionSelected: (SettingsButtonPosition) -> Unit,
    themeColors: ThemeColors
) {
    val positions = listOf(
        SettingsButtonPosition.TOP_LEFT to "Haut Gauche",
        SettingsButtonPosition.TOP_RIGHT to "Haut Droite",
        SettingsButtonPosition.BOTTOM_LEFT to "Bas Gauche",
        SettingsButtonPosition.BOTTOM_RIGHT to "Bas Droite"
    )

    Column {
        positions.chunked(2).forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { (pos, label) ->
                    PositionPreviewCard(
                        label = label,
                        selected = currentPosition == pos,
                        position = pos,
                        onClick = { onPositionSelected(pos) },
                        themeColors = themeColors,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun PositionPreviewCard(
    label: String,
    selected: Boolean,
    position: SettingsButtonPosition,
    onClick: () -> Unit,
    themeColors: ThemeColors,
    modifier: Modifier = Modifier
) {
    val borderColor = if (selected) themeColors.primary else themeColors.textSecondary.copy(alpha = 0.3f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .border(
                    width = if (selected) 2.dp else 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(themeColors.surface, RoundedCornerShape(8.dp))
                .padding(4.dp)
        ) {
            val dotAlignment = when (position) {
                SettingsButtonPosition.TOP_LEFT -> Alignment.TopStart
                SettingsButtonPosition.TOP_RIGHT -> Alignment.TopEnd
                SettingsButtonPosition.BOTTOM_LEFT -> Alignment.BottomStart
                SettingsButtonPosition.BOTTOM_RIGHT -> Alignment.BottomEnd
            }

            Box(
                modifier = Modifier
                    .size(14.dp)
                    .align(dotAlignment)
                    .background(themeColors.primary, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 11.sp,
            color = if (selected) themeColors.primary else themeColors.textSecondary,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

// ==================== BOUTON SETTINGS DRAGGABLE ====================

@Composable
fun DraggableSettingsButton(
    onClick: () -> Unit,
    themeColors: ThemeColors,
    initialOffsetX: Float,
    initialOffsetY: Float,
    onOffsetChange: (Float, Float) -> Unit,
    screenWidth: Float,
    screenHeight: Float
) {
    var offsetX by remember { mutableFloatStateOf(if (initialOffsetX == 0f) screenWidth - 80f else initialOffsetX) }
    var offsetY by remember { mutableFloatStateOf(if (initialOffsetY == 0f) screenHeight - 160f else initialOffsetY) }
    var isDragging by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging = true
                    },
                    onDragEnd = {
                        isDragging = false
                        // Snap to nearest corner
                        val snapX = if (offsetX > screenWidth / 2) screenWidth - 80f else 20f
                        val snapY = if (offsetY > screenHeight / 2) screenHeight - 160f else 80f
                        
                        offsetX = snapX
                        offsetY = snapY
                        onOffsetChange(snapX, snapY)
                    },
                    onDragCancel = {
                        isDragging = false
                    },
                    onDrag = { change: androidx.compose.ui.input.pointer.PointerInputChange, dragAmount: androidx.compose.ui.geometry.Offset ->
                        change.consumePositionChange()
                        val newX = (offsetX + dragAmount.x).coerceIn(20f, screenWidth - 80f)
                        val newY = (offsetY + dragAmount.y).coerceIn(80f, screenHeight - 160f)
                        offsetX = newX
                        offsetY = newY
                        onOffsetChange(newX, newY)
                    }
                )
            }
    ) {
        FloatingActionButton(
            onClick = {
                if (!isDragging) {
                    onClick()
                }
            },
            modifier = Modifier
                .size(56.dp),
            containerColor = themeColors.primary
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Paramètres",
                tint = themeColors.secondary
            )
        }
    }
}

// ==================== THEME SELECTOR ITEM ====================

@Composable
fun ThemeSelectorItem(
    currentTheme: CongoTheme,
    themeColors: ThemeColors,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = themeColors.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentTheme.icon,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Column {
                    Text(
                        text = currentTheme.displayName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = themeColors.textPrimary
                    )
                    Text(
                        text = "Changer le thème",
                        fontSize = 13.sp,
                        color = themeColors.textSecondary
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Sélectionner",
                tint = themeColors.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
