package io.eldohub.feature.favourites.screen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eldohub.data.favorites.database.FavoritesDatabase
import io.eldohub.data.favorites.entity.FavoriteEntity
import io.eldohub.data.favorites.repository.FavoriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoriteRepository

    init {
        val dao = FavoritesDatabase.getInstance(application).favoriteDao()
        repository = FavoriteRepository(dao)
    }

    val favorites = repository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addToFavorites(article: FavoriteEntity) {
        viewModelScope.launch { repository.addFavorite(article) }
    }

    fun removeFromFavorites(article: FavoriteEntity) {
        viewModelScope.launch { repository.removeFavorite(article) }
    }

    fun toggleFavorite(article: FavoriteEntity) {
        viewModelScope.launch {
            val url = article.url ?: return@launch
            val exists = repository.isFavorite(url)
            if (exists) {
                repository.removeByUrl(url)
            } else {
                repository.addFavorite(article)
            }
        }
    }

}
