package io.eldohub.feature.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eldohub.domain.newsFeed.model.Article
import io.eldohub.domain.search.usecase.SearchUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    object Idle : SearchUiState
    object Loading : SearchUiState
    data class Success(val articles: List<Article>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

class SearchViewModel(
    private val useCases: SearchUseCases
) : ViewModel() {

    // UI state for search results
    private val _searchState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    // Search history
    val searchHistory: StateFlow<List<String>> = useCases.getSearchHistory()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Current search query
    private val _currentQuery = MutableStateFlow("")
    val currentQuery: StateFlow<String> = _currentQuery.asStateFlow()

    // Set the current input query
    fun onQueryChange(query: String) {
        _currentQuery.value = query
    }

    // Called when user presses Enter to search
    fun onSearchSubmit() {
        val query = _currentQuery.value.trim()
        if (query.isEmpty()) return

        viewModelScope.launch {
            _searchState.value = SearchUiState.Loading

            try {
                // Save query to search history
                useCases.saveSearchQuery(query)

                // Fetch search results from API
                val articles = useCases.searchArticles(query)
                _searchState.value = SearchUiState.Success(articles)
            } catch (e: Exception) {
                _searchState.value = SearchUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    // Clear the search history
    fun clearHistory() {
        viewModelScope.launch {
            useCases.clearSearchHistory()
        }
    }

    // Reset the search state to idle
    fun resetSearch() {
        _searchState.value = SearchUiState.Idle
    }
    fun removeFromSearchHistory(query: String) {
        viewModelScope.launch {
            useCases.removeFromSearchHistoryUseCase(query)
        }
    }

}
