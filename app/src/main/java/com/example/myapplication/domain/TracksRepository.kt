package com.example.myapplication.domain

import com.example.myapplication.data.newtwork.Track



interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}