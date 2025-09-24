import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import io.eldohub.`data`.article.dao.ArticleDao
import io.eldohub.`data`.article.dao.ArticleDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _articleDao: Lazy<ArticleDao> = lazy {
    ArticleDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2,
        "55717d5514f3c2c0f493830c00ac43de", "b7b5057f8ab7b634099c94a84c7a9def") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `articles` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `dateAdded` INTEGER NOT NULL, `dateCompleted` INTEGER, `isCompleted` INTEGER NOT NULL, `imageUri` TEXT)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '55717d5514f3c2c0f493830c00ac43de')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `articles`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsArticles: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsArticles.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsArticles.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsArticles.put("content", TableInfo.Column("content", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsArticles.put("dateAdded", TableInfo.Column("dateAdded", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsArticles.put("dateCompleted", TableInfo.Column("dateCompleted", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArticles.put("isCompleted", TableInfo.Column("isCompleted", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArticles.put("imageUri", TableInfo.Column("imageUri", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysArticles: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesArticles: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoArticles: TableInfo = TableInfo("articles", _columnsArticles, _foreignKeysArticles,
            _indicesArticles)
        val _existingArticles: TableInfo = read(connection, "articles")
        if (!_infoArticles.equals(_existingArticles)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |articles(ArticleEntity).
              | Expected:
              |""".trimMargin() + _infoArticles + """
              |
              | Found:
              |""".trimMargin() + _existingArticles)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "articles")
  }

  public override fun clearAllTables() {
    super.performClear(false, "articles")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(ArticleDao::class, ArticleDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun articleDao(): ArticleDao = _articleDao.value
}
