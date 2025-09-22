package io.eldohub.`data`.search.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import io.eldohub.`data`.search.entity.SearchHistoryEntity
import javax.`annotation`.processing.Generated
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
public class SearchHistoryDao_Impl(
  __db: RoomDatabase,
) : SearchHistoryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSearchHistoryEntity: EntityInsertAdapter<SearchHistoryEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSearchHistoryEntity = object : EntityInsertAdapter<SearchHistoryEntity>()
        {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `search_history` (`id`,`query`,`timestamp`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SearchHistoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.query)
        statement.bindLong(3, entity.timestamp)
      }
    }
  }

  public override suspend fun insertSearch(entity: SearchHistoryEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfSearchHistoryEntity.insert(_connection, entity)
  }

  public override fun getAllSearches(): Flow<List<SearchHistoryEntity>> {
    val _sql: String = "SELECT * FROM search_history ORDER BY id DESC"
    return createFlow(__db, false, arrayOf("search_history")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuery: Int = getColumnIndexOrThrow(_stmt, "query")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<SearchHistoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SearchHistoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuery: String
          _tmpQuery = _stmt.getText(_columnIndexOfQuery)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item = SearchHistoryEntity(_tmpId,_tmpQuery,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteSearch(query: String) {
    val _sql: String = "DELETE FROM search_history WHERE query = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearHistory() {
    val _sql: String = "DELETE FROM search_history"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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
