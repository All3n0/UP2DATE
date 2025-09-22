package io.eldohub.data.newsFeed.datasource

import io.eldohub.data.newsFeed.model.NewsResponseData
import io.eldohub.data.common.network.Up2dateHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class RemoteNewsDataSourceImpl(
    private val client: Up2dateHttpClient
) : RemoteNewsDataSource {

    override suspend fun getEverything(params: Map<String, String>): NewsResponseData {
        val httpClient = client.getNewsClient()
        val response: HttpResponse = httpClient.get("everything") {
            params.forEach { (key, value) ->
                parameter(key, value)
            }
        }
        return response.body()
    }

    override suspend fun getTopHeadlines(params: Map<String, String>): NewsResponseData {
        val httpClient = client.getNewsClient()
        val response: HttpResponse = httpClient.get("top-headlines") {
            params.forEach { (key, value) ->
                parameter(key, value)
            }
        }
        return response.body()
    }
}
