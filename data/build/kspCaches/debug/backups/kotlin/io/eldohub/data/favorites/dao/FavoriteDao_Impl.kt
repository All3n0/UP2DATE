package io.eldohub.`data`.favorites.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import io.eldohub.`data`.favorites.entity.FavoriteEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
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
public class FavoriteDao_Impl(
  __db: RoomDatabase,
) : FavoriteDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfFavoriteEntity: EntityInsertAdapter<FavoriteEntity>

  private val __deleteAdapterOfFavoriteEntity: EntityDeleteOrUpdateAdapter<FavoriteEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfFavoriteEntity = object : EntityInsertAdapter<FavoriteEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `favorites` (`id`,`title`,`description`,`content`,`author`,`publishedAt`,`url`,`urlToImage`,`sourceName`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: FavoriteEntity) {
        statement.bindLong(1, entity.id.toLong())
        val _tmpTitle: String? = entity.title
        if (_tmpTitle == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpTitle)
        }
        val _tmpDescription: String? = entity.description
        if (_tmpDescription == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpDescription)
        }
        val _tmpContent: String? = entity.content
        if (_tmpContent == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpContent)
        }
        val _tmpAuthor: String? = entity.author
        if (_tmpAuthor == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpAuthor)
        }
        val _tmpPublishedAt: String? = entity.publishedAt
        if (_tmpPublishedAt == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpPublishedAt)
        }
        val _tmpUrl: String? = entity.url
        if (_tmpUrl == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpUrl)
        }
        val _tmpUrlToImage: String? = entity.urlToImage
        if (_tmpUrlToImage == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpUrlToImage)
        }
        val _tmpSourceName: String? = entity.sourceName
        if (_tmpSourceName == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpSourceName)
        }
      }
    }
    this.__deleteAdapterOfFavoriteEntity = object : EntityDeleteOrUpdateAdapter<FavoriteEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `favorites` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: FavoriteEntity) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertFavorite(article: FavoriteEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfFavoriteEntity.insert(_connection, article)
  }

  public override suspend fun deleteFavorite(article: FavoriteEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfFavoriteEntity.handle(_connection, article)
  }

  public override fun getAllFavorites(): Flow<List<FavoriteEntity>> {
    val _sql: String = "SELECT * FROM favorites"
    return createFlow(__db, false, arrayOf("favorites")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfAuthor: Int = getColumnIndexOrThrow(_stmt, "author")
        val _columnIndexOfPublishedAt: Int = getColumnIndexOrThrow(_stmt, "publishedAt")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfUrlToImage: Int = getColumnIndexOrThrow(_stmt, "urlToImage")
        val _columnIndexOfSourceName: Int = getColumnIndexOrThrow(_stmt, "sourceName")
        val _result: MutableList<FavoriteEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: FavoriteEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String?
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpContent: String?
          if (_stmt.isNull(_columnIndexOfContent)) {
            _tmpContent = null
          } else {
            _tmpContent = _stmt.getText(_columnIndexOfContent)
          }
          val _tmpAuthor: String?
          if (_stmt.isNull(_columnIndexOfAuthor)) {
            _tmpAuthor = null
          } else {
            _tmpAuthor = _stmt.getText(_columnIndexOfAuthor)
          }
          val _tmpPublishedAt: String?
          if (_stmt.isNull(_columnIndexOfPublishedAt)) {
            _tmpPublishedAt = null
          } else {
            _tmpPublishedAt = _stmt.getText(_columnIndexOfPublishedAt)
          }
          val _tmpUrl: String?
          if (_stmt.isNull(_columnIndexOfUrl)) {
            _tmpUrl = null
          } else {
            _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          }
          val _tmpUrlToImage: String?
          if (_stmt.isNull(_columnIndexOfUrlToImage)) {
            _tmpUrlToImage = null
          } else {
            _tmpUrlToImage = _stmt.getText(_columnIndexOfUrlToImage)
          }
          val _tmpSourceName: String?
          if (_stmt.isNull(_columnIndexOfSourceName)) {
            _tmpSourceName = null
          } else {
            _tmpSourceName = _stmt.getText(_columnIndexOfSourceName)
          }
          _item =
              FavoriteEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpAuthor,_tmpPublishedAt,_tmpUrl,_tmpUrlToImage,_tmpSourceName)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getFavoriteByUrl(url: String): FavoriteEntity? {
    val _sql: String = "SELECT * FROM favorites WHERE url = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, url)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfAuthor: Int = getColumnIndexOrThrow(_stmt, "author")
        val _columnIndexOfPublishedAt: Int = getColumnIndexOrThrow(_stmt, "publishedAt")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfUrlToImage: Int = getColumnIndexOrThrow(_stmt, "urlToImage")
        val _columnIndexOfSourceName: Int = getColumnIndexOrThrow(_stmt, "sourceName")
        val _result: FavoriteEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String?
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          }
          val _tmpDescription: String?
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          }
          val _tmpContent: String?
          if (_stmt.isNull(_columnIndexOfContent)) {
            _tmpContent = null
          } else {
            _tmpContent = _stmt.getText(_columnIndexOfContent)
          }
          val _tmpAuthor: String?
          if (_stmt.isNull(_columnIndexOfAuthor)) {
            _tmpAuthor = null
          } else {
            _tmpAuthor = _stmt.getText(_columnIndexOfAuthor)
          }
          val _tmpPublishedAt: String?
          if (_stmt.isNull(_columnIndexOfPublishedAt)) {
            _tmpPublishedAt = null
          } else {
            _tmpPublishedAt = _stmt.getText(_columnIndexOfPublishedAt)
          }
          val _tmpUrl: String?
          if (_stmt.isNull(_columnIndexOfUrl)) {
            _tmpUrl = null
          } else {
            _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          }
          val _tmpUrlToImage: String?
          if (_stmt.isNull(_columnIndexOfUrlToImage)) {
            _tmpUrlToImage = null
          } else {
            _tmpUrlToImage = _stmt.getText(_columnIndexOfUrlToImage)
          }
          val _tmpSourceName: String?
          if (_stmt.isNull(_columnIndexOfSourceName)) {
            _tmpSourceName = null
          } else {
            _tmpSourceName = _stmt.getText(_columnIndexOfSourceName)
          }
          _result =
              FavoriteEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpAuthor,_tmpPublishedAt,_tmpUrl,_tmpUrlToImage,_tmpSourceName)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteByUrl(url: String) {
    val _sql: String = "DELETE FROM favorites WHERE url = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, url)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
