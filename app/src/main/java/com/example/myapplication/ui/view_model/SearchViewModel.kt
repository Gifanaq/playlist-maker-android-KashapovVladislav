package com.example.myapplication.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.myapplication.domain.SearchHistoryRepository
import com.example.myapplication.domain.TracksRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    
    private val tracksRepository: TracksRepository = Creator.getTracksRepository(viewModelScope)
    private val historyRepository: SearchHistoryRepository = Creator.getSearchHistoryRepository()
    
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private var searchJob: Job? = null
    private var lastQuery: String? = null

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            val queries = historyRepository.getHistory()
            if (queries.isNotEmpty()) {
                _searchScreenState.value = SearchState.History(queries)
            } else {
                _searchScreenState.value = SearchState.Initial
            }
        }
    }

    fun searchDebounce(changedText: String) {
        if (changedText == lastQuery) return
        
        if (changedText.isBlank()) {
            loadHistory()
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search(changedText)
        }
    }

    fun search(whatSearch: String) {
        if (whatSearch.isBlank()) {
            loadHistory()
            return
        }
        
        lastQuery = whatSearch
        searchJob?.cancel()
        
        viewModelScope.launch {
            _searchScreenState.update { SearchState.Searching }
            

            historyRepository.addTrackToHistory(whatSearch)
            
            delay(2000L) 
            
            try {
                val list = tracksRepository.searchTracks(expression = whatSearch)
                _searchScreenState.update { SearchState.Success(list = list) }
            } catch (e: Exception) {
                _searchScreenState.update { SearchState.Fail(e.message ?: "Network Error") }
            }
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        lastQuery = null
        loadHistory()
    }


    fun clearHistory() {
        historyRepository.clearHistory()
        _searchScreenState.value = SearchState.Initial
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
