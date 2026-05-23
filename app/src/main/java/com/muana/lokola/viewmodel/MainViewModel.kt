package com.muana.lokola.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muana.lokola.util.DataSaverManager
import com.muana.lokola.util.LanguageManager
import com.muana.lokola.util.LauncherHelper
import com.muana.lokola.util.OnboardingManager
import com.muana.lokola.util.WallpaperManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val languageManager: LanguageManager,
    private val onboardingManager: OnboardingManager,
    private val dataSaverManager: DataSaverManager,
    val wallpaperManager: WallpaperManager
) : ViewModel() {

    val dataSaverEnabled: StateFlow<Boolean> = dataSaverManager.isDataSaverEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)

    val isOnboardingCompleted: StateFlow<Boolean> = onboardingManager.isOnboardingCompleted
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    val isDefaultLauncherPromptSkipped: StateFlow<Boolean> =
        onboardingManager.isDefaultLauncherPromptSkipped
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    private val _isDefaultLauncher = MutableStateFlow(LauncherHelper.isDefaultLauncher(context))
    val isDefaultLauncher: StateFlow<Boolean> = _isDefaultLauncher.asStateFlow()

    val shouldShowDefaultLauncherPrompt: StateFlow<Boolean> = combine(
        isOnboardingCompleted,
        isDefaultLauncherPromptSkipped,
        isDefaultLauncher
    ) { onboardingDone, skipped, isDefault ->
        onboardingDone && !isDefault && !skipped
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    private val _currentLanguage = MutableStateFlow("fr")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    init {
        viewModelScope.launch {
            languageManager.currentLanguage.collect { language ->
                _currentLanguage.value = language
            }
        }
    }

    fun refreshLauncherStatus() {
        _isDefaultLauncher.value = LauncherHelper.isDefaultLauncher(context)
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            onboardingManager.setOnboardingCompleted()
        }
    }

    fun skipDefaultLauncherPrompt() {
        viewModelScope.launch {
            onboardingManager.skipDefaultLauncherPrompt()
        }
    }

    fun changeLanguage(language: String) {
        viewModelScope.launch {
            languageManager.setLanguage(language)
        }
    }

    fun toggleDataSaver(enabled: Boolean) {
        viewModelScope.launch {
            dataSaverManager.setDataSaver(enabled)
        }
    }
}
