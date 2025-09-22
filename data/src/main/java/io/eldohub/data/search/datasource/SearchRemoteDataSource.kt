package io.eldohub.data.search.datasource

import io.eldohub.data.search.model.SearchResponseDto

interface SearchRemoteDataSource {
    suspend fun searchArticles(query: String, page: Int = 1): SearchResponseDto
}
