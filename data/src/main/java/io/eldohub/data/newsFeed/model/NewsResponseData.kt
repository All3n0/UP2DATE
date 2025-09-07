package io.eldohub.data.news.model

import io.eldohub.domain.news.model.NewsResponse
import io.eldohub.domain.news.model.Article
import io.eldohub.domain.news.model.Source
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseData(
    @SerialName("status")
    val status: String? = null,
    @SerialName("totalResults")
    val totalResults: Int? = null,
    @SerialName("articles")
    val articles: List<ArticleData>? = null,
)

@Serializable
data class ArticleData(
    @SerialName("source")
    val source: SourceData? = null,
    @SerialName("author")
    val author: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("urlToImage")
    val urlToImage: String? = null,
    @SerialName("publishedAt")
    val publishedAt: String? = null,
    @SerialName("content")
    val content: String? = null,
)

@Serializable
data class SourceData(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
)


// ✅ Mapping to domain models
internal fun NewsResponseData.toDomain() = NewsResponse(
    status = status,
    totalResults = totalResults,
    articles = articles?.map { it.toDomain() }
)

internal fun ArticleData.toDomain() = Article(
    source = source?.toDomain(),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

internal fun SourceData.toDomain() = Source(
    id = id,
    name = name
)
