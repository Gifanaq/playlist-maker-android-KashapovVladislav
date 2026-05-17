package com.example.myapplication.data.dto

import com.google.gson.annotations.SerializedName

data class TrackDto(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Long,
    val artworkUrl100: String?,
    val previewUrl: String?
)