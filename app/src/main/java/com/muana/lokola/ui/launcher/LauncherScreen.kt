package com.muana.lokola.ui.launcher

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muana.lokola.ui.components.DataSaverWidget
import com.muana.lokola.ui.components.LanguageFAB
import com.muana.lokola.ui.theme.*
import com.muana.lokola.util.AppLauncher
import com.muana.lokola.util.WallpaperManager
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
    dataSaverEnabled: Boolean,
    onDataSaverToggle: (Boolean) -> Unit,
    onMayebiClick: () -> Unit,
    onSettingsClick: () -> Unit,
    currentLanguage: String = "fr",
    onLanguageChange: (String) -> Unit = {}
) {
    val context = LocalContext.current

    val selectedWallpaperId by wallpaperManager.selectedWallpaperId.collectAsState(initial = 0)
    
    val currentDate = remember { LocalDate.now() }
    val formattedDate = remember {
        currentDate.format(DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRENCH))
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LauncherBackground)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header avec date et salutation
            HeaderSection(formattedDate = formattedDate)
            
            // Data Saver Widget
            DataSaverWidget(
                isEnabled = dataSaverEnabled,
                onToggle = onDataSaverToggle
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Quick Actions (Rumba, Actualités)
            QuickActionsRow()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Grille d'applications
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(getCongoAppList()) { app ->
                    CongoAppIcon(
                        app = app,
                        onClick = {
                            when (app.route) {
                                "mayebi" -> onMayebiClick()
                                "settings" -> onSettingsClick()
                                "phone" -> AppLauncher.launchDialer(context)
                                "messages" -> AppLauncher.launchMessages(context)
                                "browser" -> AppLauncher.launchBrowser(context)
                                "camera" -> AppLauncher.launchCamera(context)
                                "gallery" -> AppLauncher.launchGallery(context)
                                "music" -> AppLauncher.launchMusic(context)
                                "videos" -> AppLauncher.launchVideos(context)
                                "files" -> AppLauncher.launchFiles(context)
                                "calculator" -> AppLauncher.launchCalculator(context)
                                "calendar" -> AppLauncher.launchCalendar(context)
                                "clock" -> AppLauncher.launchClock(context)
                            }
                        }
                    )
                }
            }
            
            // Dock fixe en bas avec intents fonctionnels
            CongoDockBar(context = context)
        }
        
        // Language FAB - Bouton flottant pour switch langue
        LanguageFAB(
            currentLanguage = currentLanguage,
            onLanguageChange = onLanguageChange
        )
    }
}

@Composable
fun HeaderSection(formattedDate: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CongoBlue
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(CongoBlue, Color(0xFF0056B3))
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
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Mbote! 🇨🇩",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Boyeyi Bolamu na LokolaOS",
                    fontSize = 14.sp,
                    color = Color(0xFFF7D618),
                    fontWeight = FontWeight.Medium
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
fun CongoDockBar(context: Context) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DockItem(Icons.Default.Phone, "Téléphone", CongoGreen) {
                AppLauncher.launchDialer(context)
            }
            DockItem(Icons.Default.Message, "Messages", CongoBlue) {
                AppLauncher.launchMessages(context)
            }
            DockItem(Icons.Default.Public, "Internet", CongoDarkBlue) {
                AppLauncher.launchBrowser(context)
            }
            DockItem(Icons.Default.PhotoCamera, "Photo", CongoRed) {
                AppLauncher.launchCamera(context)
            }
        }
    }
}

@Composable
fun DockItem(icon: ImageVector, label: String, color: Color, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color),
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
            fontSize = 10.sp,
            color = LauncherTextSecondary,
            fontWeight = FontWeight.Medium,
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
