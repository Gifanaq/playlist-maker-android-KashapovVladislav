package com.example.myapplication.data.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DatabaseMock(
    private val scope: CoroutineScope,
) {
    companion object {
        private val historyList = mutableListOf<String>()
        private val playlists = mutableListOf<Playlist>()
        private val tracks = mutableListOf(
            Track(id = 1, trackName = "Yesterday (Remastered 2009)", artistName = "The Beatles", trackTime = "5:35"),
            Track(id = 2, trackName = "Владивосток 2000", artistName = "Мумий Тролль", trackTime = "2:38"),
            Track(id = 3, trackName = "Группа крови", artistName = "Кино", trackTime = "4:43"),
            Track(id = 4, trackName = "Звезда по имени Солнце", artistName = "Кино", trackTime = "3:45")
        )
        
        private val _updateTrigger = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }
    }

    private fun notifyChanged() {
        scope.launch { _updateTrigger.emit(Unit) }
    }

    // --- Работа с историей ---
    fun getHistory(): List<String> = historyList.toList()

    fun addToHistory(word: String) {
        historyList.add(word)
        notifyChanged()
    }

    // --- Работа с плейлистами ---
    fun getAllPlaylists(): Flow<List<Playlist>> = _updateTrigger.map {
        delay(300)
        playlists.map { playlist ->
            playlist.copy(tracks = tracks.filter { it.playlistId == playlist.id })
        }
    }

    // ИСПРАВЛЕНО: Теперь возвращаем плейлист С ТРЕКАМИ, чтобы на экране деталей не было "0 треков"
    fun getPlaylist(id: Long): Flow<Playlist?> = _updateTrigger.map {
        playlists.find { it.id == id }?.let { playlist ->
            playlist.copy(tracks = tracks.filter { it.playlistId == playlist.id })
        }
    }

    fun addNewPlaylist(name: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
        notifyChanged()
    }

    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
        notifyChanged()
    }

    // --- Работа с треками ---
    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
        notifyChanged()
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.forEachIndexed { index, track ->
            if (track.id == trackId) {
                tracks[index] = track.copy(playlistId = 0)
            }
        }
        notifyChanged()
    }

    fun getFavoriteTracks(): Flow<List<Track>> = _updateTrigger.map {
        delay(300)
        tracks.filter { it.favorite }
    }

    fun searchTracks(expression: String): List<Track> {
        return tracks.filter { 
            it.trackName.contains(expression, true) || it.artistName.contains(expression, true) 
        }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }
    
    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.forEachIndexed { i, t -> 
            if (t.playlistId == playlistId) tracks[i] = t.copy(playlistId = 0) 
        }
        notifyChanged()
    }
}
