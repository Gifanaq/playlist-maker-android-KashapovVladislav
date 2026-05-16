package com.example.myapplication.data.network

data class Track(
    val id: Long = 0,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String? = null, // Ссылка на обложку для экрана деталей
    val playlistId: Long = 0,          // ID плейлиста, к которому привязан трек
    val favorite: Boolean = false      // Статус "Избранное"
)