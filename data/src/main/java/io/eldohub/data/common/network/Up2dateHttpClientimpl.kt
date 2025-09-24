package io.eldohub.data.common.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class Up2dateHttpClientImpl : Up2dateHttpClient {

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
//
        private const val API_KEY = "d538bf5c55de486985f54307164fab0c"
        private const val TIME_OUT = 60_000L
        private const val TAG = "NewsApiClient"
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val newsHttpClient = HttpClient(Android) {
        expectSuccess = false

        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
        }

        install(HttpTimeout) {
            connectTimeoutMillis = TIME_OUT
            requestTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }

        install(ContentNegotiation) {
            json(Json {
                explicitNulls = false
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(TAG, "HTTP Log: $message")
                }
            }
            level = LogLevel.ALL
        }

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, request ->
                Log.e(TAG, "HTTP Exception: ${exception.localizedMessage}")
                Log.e(TAG, "Request URL: ${request.url}")
            }
        }
    }

    override fun getNewsClient(apiKey: String?): HttpClient {
        val resolvedApiKey = apiKey ?: API_KEY
        newsHttpClient.plugin(HttpSend).intercept { request ->

            request.url.parameters.append("apiKey", resolvedApiKey)
            execute(request)
        }
        return newsHttpClient
    }

}

interface Up2dateHttpClient {
    fun getNewsClient(apiKey: String? = null): HttpClient

}
