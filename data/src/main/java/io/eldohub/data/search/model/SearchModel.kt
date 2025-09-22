package io.eldohub.data.search.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchArticleDto(
    val source: SourceDto? = null,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    @SerialName("urlToImage") val urlToImage: String? = null,
    @SerialName("publishedAt") val publishedAt: String? = null,
    val content: String? = null
)

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String
)

@Serializable
data class SearchResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<SearchArticleDto>
)
