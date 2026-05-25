package com.example.myapplication.domain

interface SearchHistoryRepository {
    fun addTrackToHistory(query: String)
    suspend fun getHistory(): List<String>
    fun clearHistory()
}
