package com.example.myapplication.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.network.TracksRepositoryImpl
import com.example.myapplication.domain.TracksRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val tracksRepository: TracksRepository = TracksRepositoryImpl(viewModelScope)
    
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private var searchJob: Job? = null

    fun searchDebounce(changedText: String) {
        if (changedText.isBlank()) {
            clearSearch()
            return
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search(changedText)
        }
    }

    fun search(whatSearch: String) {
        searchJob?.cancel()
        viewModelScope.launch {
            _searchScreenState.update { SearchState.Searching }
            // Искусственная задержка для того, чтобы лоадер был виден (согласно Чек-листу)
            delay(1000L) 
            try {
                val list = tracksRepository.searchTracks(expression = whatSearch)
                _searchScreenState.update { SearchState.Success(list = list) }
            } catch (e: Exception) {
                _searchScreenState.update { SearchState.Fail(e.message ?: "Error") }
            }
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        _searchScreenState.value = SearchState.Initial
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 500L // Сделаем чуть быстрее для удобства
    }
}