package io.eldohub.feature.articles.screen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eldohub.domain.article.model.Article
import io.eldohub.domain.article.repository.ArticleRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle.asStateFlow()

    init {
        // Collect articles on start
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
}
