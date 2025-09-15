package io.eldohub.`data`.article.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import io.eldohub.`data`.article.entity.ArticleEntity
import java.util.Date
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ArticleDao_Impl(
  __db: RoomDatabase,
) : ArticleDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfArticleEntity: EntityInsertAdapter<ArticleEntity>

  private val __deleteAdapterOfArticleEntity: EntityDeleteOrUpdateAdapter<ArticleEntity>

  private val __updateAdapterOfArticleEntity: EntityDeleteOrUpdateAdapter<ArticleEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfArticleEntity = object : EntityInsertAdapter<ArticleEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `articles` (`id`,`title`,`content`,`dateAdded`,`dateCompleted`,`isCompleted`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ArticleEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.content)
        val _tmp: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(6, _tmp.toLong())
      }
    }
    this.__deleteAdapterOfArticleEntity = object : EntityDeleteOrUpdateAdapter<ArticleEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `articles` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ArticleEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfArticleEntity = object : EntityDeleteOrUpdateAdapter<ArticleEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `articles` SET `id` = ?,`title` = ?,`content` = ?,`dateAdded` = ?,`dateCompleted` = ?,`isCompleted` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ArticleEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.content)
        val _tmp: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindLong(7, entity.id)
      }
    }
  }

  public override suspend fun insert(article: ArticleEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfArticleEntity.insertAndReturnId(_connection, article)
    _result
  }

  public override suspend fun delete(article: ArticleEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfArticleEntity.handle(_connection, article)
  }

  public override suspend fun update(article: ArticleEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfArticleEntity.handle(_connection, article)
  }

  public override fun getAllArticles(): Flow<List<ArticleEntity>> {
    val _sql: String = "SELECT * FROM articles ORDER BY dateAdded DESC"
    return createFlow(__db, false, arrayOf("articles")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfDateAdded: Int = getColumnIndexOrThrow(_stmt, "dateAdded")
        val _columnIndexOfDateCompleted: Int = getColumnIndexOrThrow(_stmt, "dateCompleted")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _result: MutableList<ArticleEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArticleEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpDateAdded: Date
          val _tmpDateCompleted: Date?
          val _tmpIsCompleted: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp != 0
          _item =
              ArticleEntity(_tmpId,_tmpTitle,_tmpContent,_tmpDateAdded,_tmpDateCompleted,_tmpIsCompleted)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getArticleById(id: Long): ArticleEntity? {
    val _sql: String = "SELECT * FROM articles WHERE id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfDateAdded: Int = getColumnIndexOrThrow(_stmt, "dateAdded")
        val _columnIndexOfDateCompleted: Int = getColumnIndexOrThrow(_stmt, "dateCompleted")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _result: ArticleEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpDateAdded: Date
          val _tmpDateCompleted: Date?
          val _tmpIsCompleted: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp != 0
          _result =
              ArticleEntity(_tmpId,_tmpTitle,_tmpContent,_tmpDateAdded,_tmpDateCompleted,_tmpIsCompleted)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
