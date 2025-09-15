package io.eldohub.domain.article.usecase

import io.eldohub.domain.article.repository.ArticleRepository
import io.eldohub.domain.article.model.Article

class GetArticleByIdUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(id: Long): Article? {
        return repository.getArticleById(id)
    }
}
