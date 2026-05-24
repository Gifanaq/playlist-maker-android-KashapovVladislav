package com.example.myapplication.data.database.dao

import androidx.room.*
import com.example.myapplication.data.database.entity.PlaylistEntity
import com.example.myapplication.data.database.entity.PlaylistTrackCrossRef
import com.example.myapplication.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Transaction
    @Query("""
        SELECT playlists.*, COUNT(playlist_track_cross_ref.trackId) AS tracksCount 
        FROM playlists 
        LEFT JOIN playlist_track_cross_ref ON playlists.id = playlist_track_cross_ref.playlistId 
        GROUP BY playlists.id 
        ORDER BY createdAt DESC
    """)
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    fun getPlaylistById(playlistId: Long): Flow<PlaylistEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlaylistTrackCrossRef(crossRef: PlaylistTrackCrossRef)

    @Transaction
    @Query("""
        SELECT tracks.* FROM tracks 
        INNER JOIN playlist_track_cross_ref ON tracks.id = playlist_track_cross_ref.trackId 
        WHERE playlist_track_cross_ref.playlistId = :playlistId
    """)
    fun getTracksForPlaylist(playlistId: Long): Flow<List<TrackEntity>>

    @Query("DELETE FROM playlists WHERE id = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)

    @Query("UPDATE playlists SET coverImageUri = :coverImageUri WHERE id = :playlistId")
    suspend fun updatePlaylistCover(playlistId: Long, coverImageUri: String)


    @Query("DELETE FROM playlist_track_cross_ref WHERE playlistId = :playlistId AND trackId = :trackId")
    suspend fun deleteTrackFromPlaylist(playlistId: Long, trackId: Long)
}
