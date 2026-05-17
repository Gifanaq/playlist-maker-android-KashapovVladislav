package com.example.myapplication.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.DatabaseMock
import com.example.myapplication.data.network.Playlist
import com.example.myapplication.data.network.Track
import com.example.myapplication.domain.PlaylistsRepository
import com.example.myapplication.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PlaylistsViewModel : ViewModel() {
    // Используем Creator для получения репозиториев. 
    // Он сам подставит нужный NetworkClient и привяжет репозиторий к viewModelScope.
    private val playlistsRepository: PlaylistsRepository = Creator.getPlaylistsRepository(viewModelScope)
    private val tracksRepository: TracksRepository = Creator.getTracksRepository(viewModelScope)
    
    // Прямой доступ к базе через мок для простых операций
    private val databaseRepository = DatabaseMock(viewModelScope)

    private val _selectedTrack = MutableStateFlow<Track?>(null)
    val selectedTrack = _selectedTrack.asStateFlow()

    fun setSelectedTrack(track: Track) {
        _selectedTrack.value = track
    }

    val playlists: Flow<List<Playlist>> = flow {
        playlistsRepository.getAllPlaylists().collect { list ->
            emit(list)
        }
    }
    
    val favoriteList: Flow<List<Track>> = databaseRepository.getFavoriteTracks()

    fun createNewPlayList(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description)
        }
    }

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        tracksRepository.insertTrackToPlaylist(track, playlistId)
    }

    suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
    }

    suspend fun deleteTrackFromPlaylist(track: Track) {
        tracksRepository.deleteTrackFromPlaylist(track)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id)
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track = track).firstOrNull()
    }
}
