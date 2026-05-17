package com.example.myapplication.data.network

data class Track(
    val id: Long = 0,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String? = null,
    val previewUrl: String? = null,    // Добавили обратно для совместимости
    val playlistId: Long = 0,
    val favorite: Boolean = false
)