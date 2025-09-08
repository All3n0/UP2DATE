package io.eldohub.feature.newsfeed.screen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eldohub.domain.newsFeed.model.Article
import io.eldohub.domain.newsFeed.usecase.FetchNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class NewsFeedUiState {
    object Loading : NewsFeedUiState()
    data class Success(val articles: List<Article>) : NewsFeedUiState()
    data class Error(val message: String) : NewsFeedUiState()
}

class NewsFeedViewModel(
    private val fetchNewsUseCase: FetchNewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsFeedUiState>(NewsFeedUiState.Loading)
    val uiState: StateFlow<NewsFeedUiState> = _uiState

    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines(
        country: String = "us",
        pageSize: Int = 60,
        page: Int = 1
    ) {
        viewModelScope.launch {
            _uiState.value = NewsFeedUiState.Loading
            try {
                val response = fetchNewsUseCase.getTopHeadlines(
                    params = mapOf(
                        "country" to country,
                        "pageSize" to pageSize.toString(),
                        "page" to page.toString()
                    )
                )

                val articles = response.articles ?: emptyList()
                _uiState.value = NewsFeedUiState.Success(articles)

            } catch (e: Exception) {
                _uiState.value = NewsFeedUiState.Error(e.localizedMessage ?: "Something went wrong")
            }
        }
    }
}
