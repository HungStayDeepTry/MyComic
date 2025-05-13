package hung.deptrai.mycomic.core.data.local.database
import androidx.room.Database
import androidx.room.RoomDatabase
import hung.deptrai.mycomic.feature.explore_manga.data.local.dao.HomeMangaDao
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity

@Database(
    entities = [HomeMangaEntity::class, TagEntity::class, ChapterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeMangaDao(): HomeMangaDao
}