package com.example.myapplication.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.Playlist
import com.example.myapplication.domain.PlaylistsRepository
import kotlinx.coroutines.flow.Flow

class PlaylistViewModel(
    private val playlistId: Long
) : ViewModel() {

    // Репозиторий создается здесь, где доступен viewModelScope
    private val playlistsRepository: PlaylistsRepository = Creator.getPlaylistsRepository(viewModelScope)

    val playlist: Flow<Playlist?> = playlistsRepository.getPlaylist(playlistId)

    companion object {
        fun getViewModelFactory(playlistId: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistViewModel(playlistId) as T
                }
            }
    }
}
