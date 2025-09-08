package io.eldohub.data.newsFeed.datasource

import io.eldohub.data.common.network.Up2dateHttpClient
import io.eldohub.data.newsFeed.model.NewsResponseData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RemoteNewsDataSourceImpl(
    private val httpClientProvider: Up2dateHttpClient
) : RemoteNewsDataSource {

    private val client = httpClientProvider.getNewsClient()

    override suspend fun getEverything(params: Map<String, String>): NewsResponseData {
        return client.get("everything") {
            params.forEach { (key, value) -> parameter(key, value) }
        }.body()
    }

    override suspend fun getTopHeadlines(params: Map<String, String>): NewsResponseData {
        return client.get("top-headlines") {
            params.forEach { (key, value) -> parameter(key, value) }
        }.body()
    }
}
