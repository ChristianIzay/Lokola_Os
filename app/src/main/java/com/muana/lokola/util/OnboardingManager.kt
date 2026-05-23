package com.muana.lokola.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.onboardingDataStore by preferencesDataStore(name = "onboarding_prefs")

class OnboardingManager(private val context: Context) {

    companion object {
        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
        private val DEFAULT_LAUNCHER_SKIPPED_KEY = booleanPreferencesKey("default_launcher_skipped")
    }

    val isOnboardingCompleted: Flow<Boolean> = context.onboardingDataStore.data
        .map { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] ?: false
        }

    val isDefaultLauncherPromptSkipped: Flow<Boolean> = context.onboardingDataStore.data
        .map { preferences ->
            preferences[DEFAULT_LAUNCHER_SKIPPED_KEY] ?: false
        }

    suspend fun setOnboardingCompleted() {
        context.onboardingDataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = true
        }
    }

    suspend fun skipDefaultLauncherPrompt() {
        context.onboardingDataStore.edit { preferences ->
            preferences[DEFAULT_LAUNCHER_SKIPPED_KEY] = true
        }
    }
}
