package io.eldohub.domain.article.repository

import io.eldohub.domain.article.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun insertArticle(article: Article): Long
    suspend fun updateArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun getAllArticles(): Flow<List<Article>>
    suspend fun getArticleById(id: Long): Article?
}
