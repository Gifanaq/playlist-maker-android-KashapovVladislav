package com.example.myapplication.ui.view_model

import com.example.myapplication.data.network.Track

sealed class SearchState {
    object Initial: SearchState()
    object Searching: SearchState()
    data class Success(val list: List<Track>): SearchState()
    data class Fail(val error: String): SearchState()
    data class History(val queries: List<String>): SearchState()
}
