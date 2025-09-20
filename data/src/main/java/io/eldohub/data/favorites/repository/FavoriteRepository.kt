package io.eldohub.data.favorites.repository

import io.eldohub.data.favorites.dao.FavoriteDao
import io.eldohub.data.favorites.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val dao: FavoriteDao) {
    suspend fun addFavorite(article: FavoriteEntity) = dao.insertFavorite(article)
    suspend fun removeFavorite(article: FavoriteEntity) = dao.deleteFavorite(article)
    fun getFavorites(): Flow<List<FavoriteEntity>> = dao.getAllFavorites()

    suspend fun removeByUrl(url: String) {
        dao.deleteByUrl(url)
    }

    suspend fun isFavorite(url: String) = dao.getFavoriteByUrl(url) != null
}
