import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val dateAdded: Long = System.currentTimeMillis(),
    val dateCompleted: Long? = null,
    val isCompleted: Boolean = false,
    val imageUri: String? = null
)
