package io.eldohub.data.newsFeed.datasource

import io.eldohub.data.newsFeed.model.NewsResponseData

interface RemoteNewsDataSource {
    suspend fun getEverything(params: Map<String, String>): NewsResponseData
    suspend fun getTopHeadlines(params: Map<String, String>): NewsResponseData
}
