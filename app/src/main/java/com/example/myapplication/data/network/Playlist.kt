package com.example.myapplication.data.network

import com.example.myapplication.data.database.entity.PlaylistEntity

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverImageUri: String? = null,
    val tracksCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    var tracks: List<Track> = emptyList()
)

fun PlaylistEntity.toPlaylist(tracks: List<Track>): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        description = this.description,
        coverImageUri = this.coverImageUri,
        tracksCount = if (tracks.isNotEmpty()) tracks.size else this.tracksCount,
        createdAt = this.createdAt,
        tracks = tracks
    )
}


