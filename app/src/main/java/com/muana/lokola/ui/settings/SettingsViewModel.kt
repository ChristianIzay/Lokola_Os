package com.muana.lokola.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muana.lokola.util.DataSaverManager
import com.muana.lokola.util.LanguageManager
import com.muana.lokola.util.ThemeModeManager
import com.muana.lokola.util.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataSaverManager: DataSaverManager,
    private val languageManager: LanguageManager,
    val themeModeManager: ThemeModeManager,
    val themeManager: ThemeManager
) : ViewModel() {

    val dataSaverEnabled: StateFlow<Boolean> = dataSaverManager.isDataSaverEnabled
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    val currentLanguage: StateFlow<String> = languageManager.currentLanguage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "fr"
        )

    fun toggleDataSaver(enabled: Boolean) {
        viewModelScope.launch {
            dataSaverManager.setDataSaver(enabled)
        }
    }

    fun changeLanguage(language: String) {
        viewModelScope.launch {
            languageManager.setLanguage(language)
        }
    }
}
