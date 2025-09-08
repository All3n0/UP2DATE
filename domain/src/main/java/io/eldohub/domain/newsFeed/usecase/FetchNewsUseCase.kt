package io.eldohub.domain.newsFeed.usecase

import io.eldohub.domain.common.InternetService
import io.eldohub.domain.common.Response
import io.eldohub.domain.common.usecase.UseCaseNoInput
import io.eldohub.domain.newsFeed.model.NewsResponse
import io.eldohub.domain.newsFeed.repository.FetchNewsRepository

class FetchNewsUseCase
   ( private val fetchNewsUseCase:FetchNewsRepository,
    private val internetService:InternetService
    ) {
    suspend fun getEverything(params: Map<String, String>): NewsResponse {
        return fetchNewsUseCase.getEverything(params)
    }

    suspend fun getTopHeadlines(params: Map<String, String>): NewsResponse {
        return fetchNewsUseCase.getTopHeadlines(params)
    }
}