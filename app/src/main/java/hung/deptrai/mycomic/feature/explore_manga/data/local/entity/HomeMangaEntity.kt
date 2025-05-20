package hung.deptrai.mycomic.feature.explore_manga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "manga")
data class HomeMangaEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authorName: String,
    val artist: String,
    val coverArtLink: String?,
    val originalLang: String?,
    val commentCount: Int?,
    val createdAt: Long,
    val contentRating: String,
    val customType: Int,
    val latestUploadedChapter: String
)

enum class CustomType(val value: Int){
    POPULAR_NEW_TITLES(0),
    LATEST_UPDATES(1),
    STAFF_PICKS(2),
    SELF_PUBLISHED(3),
    FEATURE(4),
    SEASONAL(5),
    RECENTLY_ADDED(6)
}

class Converters {
    @TypeConverter
    fun fromListType(value: CustomType): Int = value.value

    @TypeConverter
    fun toListType(value: Int): CustomType = CustomType.entries.first { it.value == value }
}