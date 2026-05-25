package com.example.myapplication.data.database.dao

import androidx.room.*
import com.example.myapplication.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist")
    fun getTrackByNameAndArtist(name: String, artist: String): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE favorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Delete
    suspend fun deleteTrack(track: TrackEntity)
}
