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

class Up2dateHttpClientimpl: Up2dateHttpClient {

    companion object {
        private const val API_KEY_ACTUAL = ""
        private const val BASE_URL = "https://dummyjson.com/"
        private const val EVENTS_BASE_URL = "https://public-holidays7.p.rapidapi.com/"
        private const val WEATHER_BASE_URL = "https://open-weather13.p.rapidapi.com"

        private const val API_KEY = "API_KEY"
        private const val X_RAPID_API_KEY = "x-rapidapi-key"
        private const val TIME_OUT = 60_000L
        private const val TAG = "NetworkClient"
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val eventsHttpClient = HttpClient(Android) {
        expectSuccess = false

        defaultRequest {
            url(EVENTS_BASE_URL)
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
                ignoreUnknownKeys = false
                coerceInputValues = true
                classDiscriminator = "item_type"
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(TAG, "HTTP Log message: $message")
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

    override fun getEventsClient(apiKey: String?): HttpClient {
        val apiKey = "N8gntYIsnKmshwxYo0Hky3PquUL9p1chgQcjsnLkHXNC2AkQsW"
        eventsHttpClient.plugin(HttpSend).intercept { request ->
            request.headers {
                if (contains(X_RAPID_API_KEY)) {
                    remove(X_RAPID_API_KEY)
                }

                //  see documentation page
                append(X_RAPID_API_KEY, apiKey)
            }
            execute(request)
        }
        return eventsHttpClient
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val weatherHttpClient = HttpClient(Android) {
        expectSuccess = false

        defaultRequest {
            url(WEATHER_BASE_URL)
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
                ignoreUnknownKeys = false
                coerceInputValues = true
                classDiscriminator = "item_type"
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(TAG, "HTTP Log message: $message")
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

    override fun getWeatherClient(apiKey: String?): HttpClient {
        val apiKey = "N8gntYIsnKmshwxYo0Hky3PquUL9p1chgQcjsnLkHXNC2AkQsW"
        weatherHttpClient.plugin(HttpSend).intercept { request ->
            request.headers {
                if (contains(X_RAPID_API_KEY)) {
                    remove(X_RAPID_API_KEY)
                }

                //  see documentation page
                append(X_RAPID_API_KEY, apiKey)
            }
            execute(request)
        }
        return weatherHttpClient
    }
}

interface Up2dateHttpClient {
    fun getEventsClient(apiKey: String?): HttpClient
    fun getWeatherClient(apiKey: String?): HttpClient
}