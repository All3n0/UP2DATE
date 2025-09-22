package io.eldohub.data.search.repository

import io.eldohub.domain.newsFeed.model.Article
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchArticles(query: String, page: Int = 1): List<Article>

    suspend fun saveSearchQuery(query: String)

    fun getSearchHistory(): Flow<List<String>>

    suspend fun clearSearchHistory()

    suspend fun removeSearchQuery(query: String)  // ✅ add query parameter
}
