package hung.deptrai.mycomic.feature.explore_manga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga_statistic")
data class MangaStatisticEntity(
    @PrimaryKey val chapterid: String,
    val followCount: Int,
    val likeCount: Int
    // các trường thống kê khác
)