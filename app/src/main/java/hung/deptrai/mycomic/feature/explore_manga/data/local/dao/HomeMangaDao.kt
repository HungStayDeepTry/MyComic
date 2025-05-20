package hung.deptrai.mycomic.feature.explore_manga.data.local.dao
import androidx.room.*
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeMangaDao {

    @Transaction
    fun insertFullManga(
        manga: HomeMangaEntity,
        tags: List<TagEntity>,
        chapters: List<ChapterEntity>
    ) {
        insertManga(manga)
        insertTags(tags)
        insertChapters(chapters)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManga(manga: HomeMangaEntity)

    @Upsert
    fun upsertAll(mangaList: List<HomeMangaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAllChapters(chapters: List<ChapterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTags(tags: List<TagEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChapters(chapters: List<ChapterEntity>)

    @Query(
            """
        SELECT m.*, s.followCount
        FROM manga m
        INNER JOIN manga_statistic s ON m.id = s.mangaId
        WHERE m.customType = 0
        ORDER BY s.followCount DESC
        """
    )
    fun getPopularNewTitles(): Flow<List<HomeMangaEntity>>

    @Query(
            """
        SELECT * FROM manga
        WHERE manga.customType = 6
        ORDER BY createdAt DESC
        LIMIT :limit
    """
    )
    fun getRecentlyAdded(limit: Int = 10): Flow<List<HomeMangaEntity>>

    @Query(
        """
            SELECT * FROM chapter
            ORDER BY chapter.updatedAt DESC
            LIMIT :limit
        """
    )
    suspend fun getLatestUpdated(limit: Int = 10): Flow<List<ChapterEntity>>

    @Query(
        """
            SELECT * FROM manga
            WHERE manga.customType = 2
            LIMIT :limit
        """
    )
    fun getStaffPicks(limit: Int = 10): Flow<List<HomeMangaEntity>>

    @Query(
        """
            SELECT * FROM manga
            WHERE manga.customType = 3
            LIMIT :limit
        """
    )
    fun getSelfPublished(limit: Int = 10): Flow<List<HomeMangaEntity>>

    @Query(
        """
            SELECT * FROM manga
            WHERE manga.customType = 4
            LIMIT :limit
        """
    )
    fun getFeature(limit: Int = 10): Flow<List<HomeMangaEntity>>

    @Query(
        """
            SELECT * FROM manga
            WHERE manga.customType = 5
            LIMIT :limit
        """
    )
    fun getSeasonal(limit: Int = 10): Flow<List<HomeMangaEntity>>


    @Query(
        """
    SELECT * FROM manga
    WHERE id IN (:mangaIds)
    """
    )
    fun getMangasByIds(mangaIds: List<String>): Flow<List<HomeMangaEntity>>


    @Query("SELECT * FROM manga")
    fun getAllManga(): Flow<List<HomeMangaEntity>>

    @Query("SELECT * FROM tag WHERE mangaId = :mangaId")
    fun getTagsForManga(mangaId: String): Flow<List<TagEntity>>

    @Query("SELECT * FROM chapter WHERE mangaId = :mangaId")
    fun getChaptersForManga(mangaId: String): Flow<List<ChapterEntity>>

    @Query("DELETE FROM manga WHERE customType = :customType")
    fun clearMangaByType(customType: CustomType)

    @Query("DELETE FROM chapter")
    fun clearChapterByType()
}