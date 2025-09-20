package io.eldohub.data.favorites.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val description: String?,
    val content: String?,
    val author: String?,
    val publishedAt: String?,
    val url: String?,
    val urlToImage: String?,
    val sourceName: String?
)
