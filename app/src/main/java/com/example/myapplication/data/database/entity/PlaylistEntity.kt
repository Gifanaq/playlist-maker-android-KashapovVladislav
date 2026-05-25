package com.example.myapplication.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverImageUri: String? = null, // Новая колонка для URI обложки
    val tracksCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
