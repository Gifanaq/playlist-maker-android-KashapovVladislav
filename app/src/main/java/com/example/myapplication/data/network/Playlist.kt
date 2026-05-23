package com.example.myapplication.data.network

import com.example.myapplication.data.database.entity.PlaylistEntity

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val tracksCount: Int = 0,
    var tracks: List<Track> = emptyList()
)

fun PlaylistEntity.toPlaylist(tracks: List<Track>): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        description = this.description,
        tracksCount = if (tracks.isNotEmpty()) tracks.size else this.tracksCount,
        tracks = tracks
    )
}

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        imagePath = null,
        tracksCount = this.tracksCount
    )
}
