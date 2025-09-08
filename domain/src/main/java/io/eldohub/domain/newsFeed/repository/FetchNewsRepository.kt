package io.eldohub.domain.newsFeed.repository

import io.eldohub.domain.newsFeed.model.NewsResponse

interface FetchNewsRepository {
    suspend fun getEverything(params: Map<String, String>): NewsResponse
    suspend fun getTopHeadlines(params: Map<String, String>): NewsResponse
}