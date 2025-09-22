package io.eldohub.data.search.repository

import io.eldohub.data.search.dao.SearchHistoryDao
import io.eldohub.data.search.datasource.SearchRemoteDataSource
import io.eldohub.data.search.entity.SearchHistoryEntity
import io.eldohub.domain.newsFeed.model.Article
import io.eldohub.domain.newsFeed.model.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SearchRepositoryImpl(
    private val remoteDataSource: SearchRemoteDataSource,
    private val searchHistoryDao: SearchHistoryDao
) : io.eldohub.domain.search.repository.SearchRepository {

    override suspend fun searchArticles(query: String, page: Int): List<Article> {
        val response = remoteDataSource.searchArticles(query, page)
        return response.articles.map { dto ->
            Article(
                title = dto.title,
                description = dto.description,
                content = dto.content,
                author = dto.author,
                publishedAt = dto.publishedAt,
                url = dto.url,
                urlToImage = dto.urlToImage,
                source = dto.source?.name?.let { Source(it) }
            )
        }
    }

    override suspend fun saveSearchQuery(query: String) {
        searchHistoryDao.insertSearch(SearchHistoryEntity(query = query))
    }

    override fun getSearchHistory(): Flow<List<String>> {
        return searchHistoryDao.getAllSearches().map { it.map { e -> e.query } }
    }

    override suspend fun removeSearchQuery(query: String) {
        searchHistoryDao.deleteSearch(query)
    }



    override suspend fun clearSearchHistory() {
        withContext(Dispatchers.IO) {
            searchHistoryDao.clearHistory()
        }
    }
}
