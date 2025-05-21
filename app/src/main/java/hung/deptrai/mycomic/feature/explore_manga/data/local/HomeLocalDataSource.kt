package hung.deptrai.mycomic.feature.explore_manga.data.local

import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.MangaTagCrossRef
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun getPopularNewTitles(): Flow<List<HomeMangaEntity>>
    fun getRecentlyAdded(limit: Int): Flow<List<HomeMangaEntity>>
    fun getStaffPicks(limit: Int): Flow<List<HomeMangaEntity>>
    fun getSelfPublished(limit: Int): Flow<List<HomeMangaEntity>>
    fun getFeature(limit: Int): Flow<List<HomeMangaEntity>>
    fun getSeasonal(limit: Int): Flow<List<HomeMangaEntity>>
    fun getMangaByIds(mangaIds: List<String>): Flow<List<HomeMangaEntity>>

    fun getTagsByMangaIds(mangaIds: List<String>): Flow<Map<String, List<TagEntity>>>

    fun getLatestChapters(): Flow<List<ChapterEntity>>

    suspend fun upsertAllMangas(list: List<HomeMangaEntity>, customType: CustomType)

    suspend fun upsertByType(list: List<HomeMangaEntity>, type: CustomType)
    suspend fun upsertChapter(list: List<ChapterEntity>)


    // for tags
    suspend fun insertTags(tags: List<TagEntity>)
    suspend fun insertMangaTagCrossRefs(refs: List<MangaTagCrossRef>, mangaIds: List<String>)
}