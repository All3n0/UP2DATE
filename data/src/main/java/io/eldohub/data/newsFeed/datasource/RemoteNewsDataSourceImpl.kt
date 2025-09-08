package io.eldohub.data.newsFeed.datasource

import android.util.Log
import io.eldohub.data.common.network.Up2dateHttpClient
import io.eldohub.data.newsFeed.model.NewsResponseData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

private const val TAG = "RemoteNewsDataSrc"

class RemoteNewsDataSourceImpl(
    private val httpClientProvider: Up2dateHttpClient
) : RemoteNewsDataSource {

    private val client = httpClientProvider.getNewsClient()

    override suspend fun getEverything(params: Map<String, String>): NewsResponseData {
        Log.d(TAG, "GET /everything -> params: $params")

        return try {
            val httpResponse: HttpResponse = client.get("everything") {
                params.forEach { (key, value) -> parameter(key, value) }
            }

            Log.d(TAG, "Request sent. HTTP status = ${httpResponse.status.value}")
            // optionally log raw text (careful with large payloads)
            // Log.d(TAG, "Raw response body: ${httpResponse.bodyAsText()}")

            val body = httpResponse.body<NewsResponseData>()
            Log.d(TAG, "Parsed response: $body")
            body
        } catch (e: Exception) {
            Log.e(TAG, "Error while requesting /everything: ${e.localizedMessage}", e)
            throw e
        }
    }

    override suspend fun getTopHeadlines(params: Map<String, String>): NewsResponseData {
        Log.d(TAG, "GET /top-headlines -> params: $params")

        return try {
            val httpResponse: HttpResponse = client.get("top-headlines") {
                params.forEach { (key, value) -> parameter(key, value) }
            }

            Log.d(TAG, "Request sent. HTTP status = ${httpResponse.status.value}")
            // Log.d(TAG, "Raw response body: ${httpResponse.bodyAsText()}")

            val body = httpResponse.body<NewsResponseData>()
            Log.d(TAG, "Parsed response: $body")
            body
        } catch (e: Exception) {
            Log.e(TAG, "Error while requesting /top-headlines: ${e.localizedMessage}", e)
            throw e
        }
    }
}
