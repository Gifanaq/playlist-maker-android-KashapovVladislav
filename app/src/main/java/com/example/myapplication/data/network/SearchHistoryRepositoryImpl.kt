package com.example.myapplication.data.network

import com.example.myapplication.data.preferences.SearchHistoryPreferences
import com.example.myapplication.domain.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val searchHistoryPreferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override fun addTrackToHistory(query: String) {
        searchHistoryPreferences.addEntry(query)
    }

    override suspend fun getHistory(): List<String> {
        return searchHistoryPreferences.getEntries()
    }

    override fun clearHistory() {
        searchHistoryPreferences.clear()
    }
}
