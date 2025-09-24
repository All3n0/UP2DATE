package io.eldohub.data.article.repository

import ArticleEntity
import io.eldohub.data.article.datasource.ArticleLocalDataSource
import io.eldohub.domain.article.model.Article
import io.eldohub.domain.article.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class ArticleRepositoryImpl(
    private val localDataSource: ArticleLocalDataSource
) : ArticleRepository {

    override suspend fun insertArticle(article: Article): Long {
        return localDataSource.insertArticle(article.toEntity())
    }

    override suspend fun updateArticle(article: Article) {
        localDataSource.updateArticle(article.toEntity())
    }

    override suspend fun deleteArticle(article: Article) {
        localDataSource.deleteArticle(article.toEntity())
    }

    override fun getAllArticles(): Flow<List<Article>> {
        return localDataSource.getAllArticles().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getArticleById(id: Long): Article? {
        return localDataSource.getArticleById(id)?.toDomain()
    }
}

// ✅ Mappers
fun ArticleEntity.toDomain() = Article(
    id = id,
    title = title,
    content = content,
    dateAdded = Date(dateAdded),
    dateCompleted = dateCompleted?.let { Date(it) },
    isCompleted = isCompleted,
    imageUri = imageUri // 👈 carry over
)

fun Article.toEntity() = ArticleEntity(
    id = id,
    title = title,
    content = content,
    dateAdded = dateAdded.time,
    dateCompleted = dateCompleted?.time,
    isCompleted = isCompleted,
    imageUri = imageUri // 👈 carry over
)

