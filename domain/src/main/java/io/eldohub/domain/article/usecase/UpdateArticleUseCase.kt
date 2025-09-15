package io.eldohub.domain.article.usecase

import io.eldohub.domain.article.model.Article
import io.eldohub.domain.article.repository.ArticleRepository

class UpdateArticleUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.updateArticle(article)
    }
}
