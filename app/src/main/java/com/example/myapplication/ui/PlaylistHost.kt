package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.Screens
import com.example.myapplication.ui.activity.MainScreen
import com.example.myapplication.ui.activity.SearchScreen
import com.example.myapplication.ui.activity.SettingsScreen

@Composable
fun PlaylistHost(navController: NavHostController) {
    fun navigateToSearch() {
        navController.navigate(Screens.Songs.name)
    }

    fun navigateToSettings() {
        navController.navigate(Screens.Settings.name)
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    NavHost(
        navController = navController,
        startDestination = Screens.Main.name
    ) {
        composable(Screens.Main.name) {
            MainScreen(
                onSearchClick = { navigateToSearch() },
                onSettingsClick = { navigateToSettings() }
            )
        }
        composable(Screens.Songs.name) {
            SearchScreen(
                onBackClick = { navigateBack() }
            )
        }
        composable(Screens.Settings.name) {
            SettingsScreen(
                onBackClick = { navigateBack() }
            )
        }
    }
}