package com.muana.lokola.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muana.lokola.ui.launcher.DefaultLauncherScreen
import com.muana.lokola.ui.launcher.LauncherScreen
import com.muana.lokola.ui.mayebi.LessonDetailScreen
import com.muana.lokola.ui.mayebi.MayebiScreen
import com.muana.lokola.ui.onboarding.OnboardingScreen
import com.muana.lokola.ui.settings.SettingsScreen
import com.muana.lokola.ui.wallpaper.WallpaperPickerScreen
import com.muana.lokola.viewmodel.MainViewModel

@Composable
fun LokolaNavHost(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()
    val shouldShowDefaultLauncherPrompt by viewModel.shouldShowDefaultLauncherPrompt.collectAsState()

    val startDestination = when {
        !isOnboardingCompleted -> Screen.Onboarding.route
        shouldShowDefaultLauncherPrompt -> Screen.SetDefaultLauncher.route
        else -> Screen.Launcher.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onComplete = {
                    viewModel.completeOnboarding()
                    val destination = if (viewModel.isDefaultLauncher.value) {
                        Screen.Launcher.route
                    } else {
                        Screen.SetDefaultLauncher.route
                    }
                    navController.navigate(destination) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SetDefaultLauncher.route) {
            DefaultLauncherScreen(
                viewModel = viewModel,
                onContinue = {
                    viewModel.skipDefaultLauncherPrompt()
                    navController.navigate(Screen.Launcher.route) {
                        popUpTo(Screen.SetDefaultLauncher.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Launcher.route) {
            val currentLanguage by viewModel.currentLanguage.collectAsState()
            val dataSaverEnabled by viewModel.dataSaverEnabled.collectAsState()

            LauncherScreen(
                wallpaperManager = viewModel.wallpaperManager,
                dataSaverEnabled = dataSaverEnabled,
                onDataSaverToggle = viewModel::toggleDataSaver,
                onMayebiClick = { navController.navigate(Screen.Mayebi.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) },
                currentLanguage = currentLanguage,
                onLanguageChange = { newLanguage ->
                    viewModel.changeLanguage(newLanguage)
                }
            )
        }

        composable(Screen.Mayebi.route) {
            MayebiScreen(
                onLessonClick = { lessonId ->
                    navController.navigate(Screen.LessonDetail.createRoute(lessonId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.LessonDetail.route,
            arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getInt("lessonId") ?: 0
            LessonDetailScreen(
                lessonId = lessonId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onWallpaperClick = { navController.navigate(Screen.WallpaperPicker.route) }
            )
        }

        composable(Screen.WallpaperPicker.route) {
            WallpaperPickerScreen(
                wallpaperManager = viewModel.wallpaperManager,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
