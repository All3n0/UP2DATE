package io.eldohub.domain.article.usecase

import io.eldohub.domain.article.model.Article
import io.eldohub.domain.article.repository.ArticleRepository

class DeleteArticleUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.deleteArticle(article)
    }
}
