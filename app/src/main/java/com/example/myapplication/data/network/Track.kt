package com.example.myapplication.data.network

import com.example.myapplication.data.database.entity.TrackEntity

data class Track(
    val id: Long = 0,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String? = null,
    val previewUrl: String? = null,
    val favorite: Boolean = false
)


fun TrackEntity.toTrack(): Track {
    return Track(
        id = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        artworkUrl100 = this.artworkUrl100,
        previewUrl = this.previewUrl,
        favorite = this.favorite
    )
}

fun Track.toEntity(): TrackEntity {
    return TrackEntity(
        id = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        artworkUrl100 = this.artworkUrl100,
        previewUrl = this.previewUrl,
        favorite = this.favorite
    )
}
