package com.example.myapplication.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String?,
    val previewUrl: String?,
    val favorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis() // Чтобы сортировать по времени добавления
)
