package hung.deptrai.mycomic.feature.explore_manga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapter")
data class ChapterEntity(
    @PrimaryKey val id: String,
    val volume: String,
    val chapter: String,
    val title: String?,
    val translatedLang: String,
    val updatedAt: String,
    val mangaId: String // Foreign key reference
)
