package io.eldohub.data.article.dao

import ArticleEntity
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity): Long

    @Update
    suspend fun update(article: ArticleEntity)

    @Delete
    suspend fun delete(article: ArticleEntity)

    @Query("SELECT * FROM articles ORDER BY dateAdded DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    suspend fun getArticleById(id: Long): ArticleEntity?
}
