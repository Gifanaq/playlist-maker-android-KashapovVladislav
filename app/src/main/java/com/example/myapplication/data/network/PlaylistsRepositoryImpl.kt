package com.example.myapplication.data.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.core.net.toUri
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.entity.PlaylistEntity
import com.example.myapplication.domain.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PlaylistsRepositoryImpl(
    database: AppDatabase,
    private val context: Context
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

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        val internalUri = coverImageUri?.let { saveImageToInternalStorage(it) }
        playlistsDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
                coverImageUri = internalUri
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistsDao.deletePlaylist(id)
    }

    override suspend fun updatePlaylistCover(playlistId: Long, coverImageUri: String) {
        val internalUri = saveImageToInternalStorage(coverImageUri)
        playlistsDao.updatePlaylistCover(playlistId, internalUri)
    }

    override suspend fun deleteTrackFromPlaylist(playlistId: Long, trackId: Long) {
        playlistsDao.deleteTrackFromPlaylist(playlistId, trackId)
    }


    private suspend fun saveImageToInternalStorage(uriString: String): String = withContext(Dispatchers.IO) {
        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "playlist_covers")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        
        val file = File(filePath, "cover_${System.currentTimeMillis()}.jpg")
        val uri = uriString.toUri()

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                BitmapFactory
                    .decodeStream(inputStream)
                    ?.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            }
        }
        

        file.toUri().toString()
    }
}
