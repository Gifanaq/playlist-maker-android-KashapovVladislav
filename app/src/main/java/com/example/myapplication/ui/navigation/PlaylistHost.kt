package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.view_model.PlaylistViewModel
import com.example.myapplication.ui.view_model.PlaylistsViewModel

@Composable
fun PlaylistHost(navController: NavHostController) {
    val playlistsViewModel: PlaylistsViewModel = viewModel()

    fun navigateBack() {
        navController.popBackStack()
    }

    NavHost(
        navController = navController,
        startDestination = Screens.Main.name
    ) {
        composable(Screens.Main.name) {
            MainScreen(
                onSearchClick = { navController.navigate(Screens.Songs.name) },
                onSettingsClick = { navController.navigate(Screens.Settings.name) },
                onFavoritesClick = { navController.navigate(Screens.Favorites.name) },
                onPlaylistsClick = { navController.navigate(Screens.Playlists.name) }
            )
        }

        composable(Screens.Songs.name) {
            SearchScreen(
                playlistsViewModel = playlistsViewModel,
                onBackClick = { navigateBack() },
                onTrackClick = { navController.navigate(Screens.TrackDetails.name) }
            )
        }

        composable(Screens.Settings.name) {
            SettingsScreen(onBackClick = { navigateBack() })
        }

        composable(Screens.Favorites.name) {
            FavoritesScreen(
                playlistsViewModel = playlistsViewModel,
                onBackClick = { navigateBack() }
            )
        }

        composable(Screens.Playlists.name) {
            PlaylistsScreen(
                playlistsViewModel = playlistsViewModel,
                onBackClick = { navigateBack() },
                onAddNewPlaylistClick = { navController.navigate(Screens.CreatePlaylist.name) },
                onPlaylistClick = { playlistId ->
                    navController.navigate("${Screens.PlaylistDetails.name}/$playlistId")
                }
            )
        }

        composable(
            route = "${Screens.PlaylistDetails.name}/{playlistId}",
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            val playlistViewModel: PlaylistViewModel = viewModel(
                factory = PlaylistViewModel.getViewModelFactory(playlistId)
            )
            PlaylistScreen(
                playlistViewModel = playlistViewModel,
                onBackClick = { navigateBack() }
            )
        }

        composable(Screens.CreatePlaylist.name) {
            CreatePlaylistScreen(
                playlistsViewModel = playlistsViewModel,
                onBackClick = { navigateBack() }
            )
        }

        composable(Screens.TrackDetails.name) {
            TrackDetailsScreen(
                playlistsViewModel = playlistsViewModel,
                onBackClick = { navigateBack() }
            )
        }
    }
}
