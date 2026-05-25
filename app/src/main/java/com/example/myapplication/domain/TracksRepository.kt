package com.example.myapplication.domain

import com.example.myapplication.data.network.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(track: Track)

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)

    fun deleteTracksByPlaylistId(playlistId: Long)
}
