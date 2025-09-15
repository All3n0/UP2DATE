package io.eldohub.domain.article.model

import java.util.Date

data class Article(
    val id: Long = 0,
    val title: String,
    val content: String,
    val dateAdded: Date = Date(),
    val dateCompleted: Date? = null,
    val isCompleted: Boolean = false
)
