package hung.deptrai.mycomic.feature.explore_manga.data.local.dao
import androidx.room.*
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity

@Dao
interface HomeMangaDao {

    @Transaction
    suspend fun insertFullManga(
        manga: HomeMangaEntity,
        tags: List<TagEntity>,
        chapters: List<ChapterEntity>
    ) {
        insertManga(manga)
        insertTags(tags)
        insertChapters(chapters)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(manga: HomeMangaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<TagEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    @Query("SELECT * FROM manga")
    suspend fun getAllManga(): List<HomeMangaEntity>

    @Query("SELECT * FROM tag WHERE mangaId = :mangaId")
    suspend fun getTagsForManga(mangaId: String): List<TagEntity>

    @Query("SELECT * FROM chapter WHERE mangaId = :mangaId")
    suspend fun getChaptersForManga(mangaId: String): List<ChapterEntity>

    @Query("DELETE FROM manga")
    suspend fun clearAllManga()
}