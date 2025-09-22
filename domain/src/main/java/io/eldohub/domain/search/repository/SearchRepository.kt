package io.eldohub.domain.search.repository

import io.eldohub.domain.newsFeed.model.Article
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchArticles(query: String, page: Int = 1): List<Article>

    suspend fun saveSearchQuery(query: String)

    fun getSearchHistory(): Flow<List<String>>
    suspend fun removeSearchQuery(query:String)
    suspend fun clearSearchHistory()
}
