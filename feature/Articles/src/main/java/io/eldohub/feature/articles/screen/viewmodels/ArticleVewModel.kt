package io.eldohub.feature.articles.screen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eldohub.domain.article.model.Article
import io.eldohub.domain.article.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class ArticleViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles = _articles.asStateFlow()

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle = _selectedArticle.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllArticles().collect { list ->
                _articles.value = list
            }
        }
    }

    fun loadArticleById(id: Long) {
        viewModelScope.launch {
            _selectedArticle.value = repository.getArticleById(id)
        }
    }

    fun addArticle(article: Article) {
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }

    fun updateArticle(article: Article) {
        viewModelScope.launch {
            repository.updateArticle(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }

    /**
     * Helper for screens: create an Article from simple fields.
     * Note: Article has dateAdded: Date, so we set dateAdded here (not `date`).
     */
    fun createArticle(
        title: String,
        content: String,
        dateAdded: Date = Date(),
        dateCompleted: Date? = null,
        isCompleted: Boolean = false
    ) {
        viewModelScope.launch {
            val article = Article(
                id = 0L, // let DB autogenerate
                title = title,
                content = content,
                dateAdded = dateAdded,
                dateCompleted = dateCompleted,
                isCompleted = isCompleted
            )
            repository.insertArticle(article)
        }
    }
}
