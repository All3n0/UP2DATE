package io.eldohub.data.search.datasource

import io.eldohub.data.common.network.Up2dateHttpClient
import io.eldohub.data.search.model.SearchResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchRemoteDataSourceImpl(
    private val client: Up2dateHttpClient
) : SearchRemoteDataSource {

    override suspend fun searchArticles(query: String, page: Int): SearchResponseDto {
        val httpClient = client.getNewsClient()
        return httpClient.get("everything") {
            parameter("q", query)
            parameter("page", page)
            parameter("pageSize", 20)
            // optional: parameter("sortBy", "relevancy")
        }.body()
    }
}
