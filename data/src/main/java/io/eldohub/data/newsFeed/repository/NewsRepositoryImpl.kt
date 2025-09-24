package io.eldohub.data.newsFeed.repository

import io.eldohub.data.newsFeed.local.NewsDao
import io.eldohub.data.newsFeed.entity.NewsEntity
import io.eldohub.data.newsFeed.model.toDomain
import io.eldohub.data.newsFeed.datasource.RemoteNewsDataSource
import io.eldohub.domain.newsFeed.model.NewsResponse
import io.eldohub.domain.newsFeed.repository.FetchNewsRepository

class NewsRepositoryImpl(
    private val remoteDataSource: RemoteNewsDataSource
) : FetchNewsRepository {

    override suspend fun getEverything(params: Map<String, String>): NewsResponse {
        val response = remoteDataSource.getEverything(params)
        return response.toDomain()
    }

    override suspend fun getTopHeadlines(params: Map<String, String>): NewsResponse {
        val response = remoteDataSource.getTopHeadlines(params)
        return response.toDomain()
    }
}

