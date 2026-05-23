package com.muana.lokola.util

import android.content.Context
import com.muana.lokola.R
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.wallpaperDataStore: DataStore<Preferences> by preferencesDataStore(name = "wallpaper_prefs")

@Singleton
class WallpaperManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val wallpaperKey = intPreferencesKey("selected_wallpaper_id")

    // Get the currently selected wallpaper ID (default is 0 for no custom wallpaper or first one)
    val selectedWallpaperId: Flow<Int> = context.wallpaperDataStore.data
        .map { preferences ->
            preferences[wallpaperKey] ?: 0
        }

    // Save the selected wallpaper ID
    suspend fun setWallpaper(id: Int) {
        context.wallpaperDataStore.edit { preferences ->
            preferences[wallpaperKey] = id
        }
    }

    // List of available wallpapers (Resource IDs)
    fun getAvailableWallpapers(): List<Int> {
        return listOf(
            R.raw.wallpaper_01,
            R.raw.wallpaper_02,
            R.raw.wallpaper_03,
            R.raw.wallpaper_04,
            R.raw.wallpaper_05,
            R.raw.wallpaper_06,
            R.raw.wallpaper_07,
            R.raw.wallpaper_08,
            R.raw.wallpaper_09,
            R.raw.wallpaper_10,
            R.raw.wallpaper_11
        )
    }
}
