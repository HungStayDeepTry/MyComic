package hung.deptrai.mycomic.feature.explore_manga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class HomeMangaEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authorName: String,
    val artist: String,
    val coverArtLink: String?,
    val commentCount: Int?,
    val scanlationGroup: String?
)
