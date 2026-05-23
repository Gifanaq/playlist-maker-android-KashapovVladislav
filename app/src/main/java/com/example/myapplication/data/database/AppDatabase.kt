package com.example.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.database.dao.PlaylistsDao
import com.example.myapplication.data.database.dao.TracksDao
import com.example.myapplication.data.database.entity.PlaylistEntity
import com.example.myapplication.data.database.entity.PlaylistTrackCrossRef
import com.example.myapplication.data.database.entity.TrackEntity

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tracksDao(): TracksDao

    abstract fun playlistsDao(): PlaylistsDao
}
