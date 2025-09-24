package io.eldohub.domain.search.usecase

import io.eldohub.domain.newsFeed.model.Article
import io.eldohub.domain.search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

// Use case: search articles by query
class SearchArticlesUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String, page: Int = 1): List<Article> {
        return repository.searchArticles(query, page)
    }
}

// Use case: save search query to history
class SaveSearchQueryUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String) {
        repository.saveSearchQuery(query)
    }
}
class RemoveSearchQueryUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String) {
        repository.removeSearchQuery(query)
    }
}

// Use case: get all search history
class GetSearchHistoryUseCase(private val repository: SearchRepository) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getSearchHistory()
    }
}

// Use case: clear search history
class ClearSearchHistoryUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke() {
        repository.clearSearchHistory()
    }
}



data class SearchUseCases(
    val searchArticles: SearchArticlesUseCase,
    val saveSearchQuery: SaveSearchQueryUseCase,
    val getSearchHistory: GetSearchHistoryUseCase,
    val clearSearchHistory: ClearSearchHistoryUseCase,
    val removeFromSearchHistoryUseCase: RemoveSearchQueryUseCase
)
