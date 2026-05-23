package com.example.myapplication.data.network

import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.entity.PlaylistEntity
import com.example.myapplication.domain.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase
) : PlaylistsRepository {

    private val playlistsDao = database.playlistsDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistsDao.getPlaylistById(playlistId).flatMapLatest { entity ->
            playlistsDao.getTracksForPlaylist(playlistId).map { trackEntities ->
                entity?.toPlaylist(trackEntities.map { it.toTrack() })
            }
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistsDao.getAllPlaylists().map { entities ->
            entities.map { it.toPlaylist(emptyList()) }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        playlistsDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
                imagePath = null
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistsDao.deletePlaylist(id)
    }
}
