package io.eldohub.data.article.datasource

import ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDataSource {
    suspend fun insertArticle(article: ArticleEntity): Long
    suspend fun updateArticle(article: ArticleEntity)
    suspend fun deleteArticle(article: ArticleEntity)
    fun getAllArticles(): Flow<List<ArticleEntity>>
    suspend fun getArticleById(id: Long): ArticleEntity?
}
