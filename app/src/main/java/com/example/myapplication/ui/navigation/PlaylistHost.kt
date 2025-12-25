package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.FavoritesScreen
import com.example.myapplication.ui.screens.MainScreen
import com.example.myapplication.ui.screens.PlaylistsScreen
import com.example.myapplication.ui.screens.SearchScreen
import com.example.myapplication.ui.screens.SettingsScreen


@Composable
fun PlaylistHost(navController: NavHostController) {
    fun navigateToSearch() {
        navController.navigate(Screens.Songs.name)
    }

    fun navigateToSettings() {
        navController.navigate(Screens.Settings.name)
    }

    fun navigateToFavorites() {
        navController.navigate(Screens.Favorites.name)
    }

    fun navigateToPlaylists() {
        navController.navigate(Screens.Playlists.name)
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
                onSettingsClick = { navigateToSettings() },
                onFavoritesClick = { navigateToFavorites() },
                onPlaylistsClick = { navigateToPlaylists() }
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
        composable(Screens.Favorites.name) {
            FavoritesScreen(
                onBackClick = { navigateBack() }
            )
        }
        composable(Screens.Playlists.name) {
            PlaylistsScreen(
                onBackClick = { navigateBack() }
            )
        }
    }
}