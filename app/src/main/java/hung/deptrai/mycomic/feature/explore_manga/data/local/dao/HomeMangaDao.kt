package hung.deptrai.mycomic.feature.explore_manga.data.local.dao
import androidx.room.*
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.MangaTagCrossRef
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity
import kotlinx.coroutines.flow.Flow

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
    suspend fun upsertAll(mangaList: List<HomeMangaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAllChapters(chapters: List<ChapterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<TagEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    @Query(
            """
        SELECT * from manga where manga.customType = 0
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
    fun getLatestUpdated(limit: Int = 10): Flow<List<ChapterEntity>>

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

    @Query("SELECT * FROM chapter WHERE mangaId = :mangaId")
    fun getChaptersForManga(mangaId: String): Flow<List<ChapterEntity>>

    // tag
    @Transaction
    @Query("""
    SELECT * FROM tag 
    INNER JOIN manga_tag_cross_ref 
    ON tag.id = manga_tag_cross_ref.tagId 
    WHERE manga_tag_cross_ref.mangaId IN (:mangaIds)
""")
    fun getTagsByMangaIds(mangaIds: List<String>): Flow<List<TagEntity>>

    @Query("SELECT * FROM manga_tag_cross_ref WHERE mangaId IN (:mangaIds)")
    fun getMangaTagCrossRefsByMangaIds(mangaIds: List<String>): Flow<List<MangaTagCrossRef>>

    @Query("SELECT * FROM tag")
    fun getAllTagsFlow(): Flow<List<TagEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaTagCrossRefs(crossRefs: List<MangaTagCrossRef>)

    @Query("DELETE FROM manga WHERE customType = :customType")
    fun clearMangaByType(customType: CustomType)

    @Query("DELETE FROM chapter")
    fun clearChapterByType()

    // tag
    @Query("DELETE FROM manga_tag_cross_ref WHERE mangaId IN (:mangaIds)")
    suspend fun clearMangaTagCrossRefsByMangaIds(mangaIds: List<String>)

}