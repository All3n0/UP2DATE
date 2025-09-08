package io.eldohub.data.newsFeed.repository

import io.eldohub.data.newsFeed.local.NewsDao
import io.eldohub.data.newsFeed.entity.NewsEntity
import io.eldohub.data.newsFeed.model.toDomain
import io.eldohub.data.newsFeed.datasource.RemoteNewsDataSource
import io.eldohub.domain.newsFeed.model.NewsResponse
import io.eldohub.domain.newsFeed.repository.FetchNewsRepository

class NewsRepositoryImpl(
    private val remoteDataSource: RemoteNewsDataSource,
    private val newsDao: NewsDao
) : FetchNewsRepository {

    override suspend fun getEverything(params: Map<String, String>): NewsResponse {
        val response = remoteDataSource.getEverything(params)
        cacheArticles(response)
        return response.toDomain()
    }

    override suspend fun getTopHeadlines(params: Map<String, String>): NewsResponse {
        val response = remoteDataSource.getTopHeadlines(params)
        cacheArticles(response)
        return response.toDomain()
    }

    private suspend fun cacheArticles(response: io.eldohub.data.newsFeed.model.NewsResponseData) {
        val entities = response.articles?.map {
            NewsEntity(
                sourceId = it.source?.id,
                sourceName = it.source?.name,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        } ?: emptyList()

        newsDao.clearNews()
        newsDao.insertNews(entities)
    }
}
