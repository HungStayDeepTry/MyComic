package hung.deptrai.mycomic.core.data.local.database
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hung.deptrai.mycomic.feature.explore_manga.data.local.dao.HomeMangaDao
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.Converters
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.MangaStatisticEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.MangaTagCrossRef

@Database(
    entities = [HomeMangaEntity::class, TagEntity::class, ChapterEntity::class, MangaStatisticEntity::class, MangaTagCrossRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeMangaDao(): HomeMangaDao
}