package hung.deptrai.mycomic.feature.explore_manga.data.local

import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import kotlinx.coroutines.flow.Flow

interface HomeLocalDataSource {
    fun getPopularNewTitles(): Flow<List<HomeMangaEntity>>
    fun getRecentlyAdded(limit: Int): Flow<List<HomeMangaEntity>>
    fun getStaffPicks(limit: Int): Flow<List<HomeMangaEntity>>
    fun getSelfPublished(limit: Int): Flow<List<HomeMangaEntity>>
    fun getFeature(limit: Int): Flow<List<HomeMangaEntity>>
    fun getSeasonal(limit: Int): Flow<List<HomeMangaEntity>>
    fun getMangaByIds(mangaIds: List<String>): Flow<List<HomeMangaEntity>>

    fun upsertByType(list: List<HomeMangaEntity>, type: CustomType)
    fun upsertChapter(list: List<ChapterEntity>)
}