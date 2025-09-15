package io.eldohub.data.article.datasource

import ArticleEntity
import io.eldohub.data.article.dao.ArticleDao
import kotlinx.coroutines.flow.Flow

class ArticleLocalDataSourceImpl(
    private val articleDao: ArticleDao
) : ArticleLocalDataSource {

    override suspend fun insertArticle(article: ArticleEntity): Long {
        return articleDao.insert(article)
    }

    override suspend fun updateArticle(article: ArticleEntity) {
        articleDao.update(article)
    }

    override suspend fun deleteArticle(article: ArticleEntity) {
        articleDao.delete(article)
    }

    override fun getAllArticles(): Flow<List<ArticleEntity>> {
        return articleDao.getAllArticles()
    }

    override suspend fun getArticleById(id: Long): ArticleEntity? {
        return articleDao.getArticleById(id)
    }
}
