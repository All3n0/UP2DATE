package io.eldohub.domain.article.usecase

import io.eldohub.domain.article.repository.ArticleRepository

class GetArticlesUseCase(
    private val repository: ArticleRepository
) {
    operator fun invoke() = repository.getAllArticles()
}
