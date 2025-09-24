package io.eldohub.data.article.model

import java.util.Date

data class ArticleModel(
    val id: Long = 0,
    val title: String,
    val content: String,
    val dateAdded: Date,
    val dateCompleted: Date? = null,
    val isCompleted: Boolean = false,
    val imageUri: String? = null
)
