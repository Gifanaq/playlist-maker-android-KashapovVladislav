package com.example.myapplication.data.network

import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.entity.PlaylistTrackCrossRef
import com.example.myapplication.data.dto.TracksSearchRequest
import com.example.myapplication.data.dto.TracksSearchResponse
import com.example.myapplication.domain.NetworkClient
import com.example.myapplication.domain.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: AppDatabase
) : TracksRepository {
    
    private val tracksDao = database.tracksDao()
    private val playlistsDao = database.playlistsDao()

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        
        if (response.resultCode == 200) {
            return (response as TracksSearchResponse).results.map {
                Track(
                    id = it.trackId,
                    trackName = it.trackName,
                    artistName = it.artistName,
                    trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(it.trackTimeMillis),
                    artworkUrl100 = it.artworkUrl100,
                    previewUrl = it.previewUrl
                )
            }
        } else {
            throw IOException("Network connection failed")
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return tracksDao.getTrackByNameAndArtist(track.trackName, track.artistName)
            .map { it?.toTrack() }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        tracksDao.insertTrack(track.toEntity())
        playlistsDao.insertPlaylistTrackCrossRef(PlaylistTrackCrossRef(playlistId, track.id))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {

    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        tracksDao.insertTrack(track.copy(favorite = isFavorite).toEntity())
    }

    override fun deleteTracksByPlaylistId(playlistId: Long) {

    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return tracksDao.getFavoriteTracks().map { entities ->
            entities.map { it.toTrack() }
        }
    }
}
