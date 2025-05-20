package hung.deptrai.mycomic.feature.explore_manga.data.local

import hung.deptrai.mycomic.feature.explore_manga.data.local.dao.HomeMangaDao
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(
    private val dao: HomeMangaDao
) : HomeLocalDataSource{
    override fun getPopularNewTitles(): Flow<List<HomeMangaEntity>> {
        return dao.getPopularNewTitles()
    }

    override fun getRecentlyAdded(limit: Int): Flow<List<HomeMangaEntity>> {
        return dao.getRecentlyAdded()
    }

    override fun getStaffPicks(limit: Int): Flow<List<HomeMangaEntity>> {
        return dao.getStaffPicks()
    }

    override fun getSelfPublished(limit: Int): Flow<List<HomeMangaEntity>> {
        return dao.getSelfPublished()
    }

    override fun getFeature(limit: Int): Flow<List<HomeMangaEntity>> {
        return dao.getFeature()
    }

    override fun getSeasonal(limit: Int): Flow<List<HomeMangaEntity>> {
        return dao.getSeasonal()
    }

    override fun getMangaByIds(mangaIds: List<String>): Flow<List<HomeMangaEntity>> {
        return dao.getMangasByIds(mangaIds)
    }

    override fun getLatestChapters(): Flow<List<ChapterEntity>> {
        return dao.getLatestUpdated()
    }

    override fun upsertByType(list: List<HomeMangaEntity>, type: CustomType) {
        dao.clearMangaByType(type)
        dao.upsertAll(list)
    }

    override fun upsertChapter(list: List<ChapterEntity>) {
        dao.clearChapterByType()
        dao.upsertAllChapters(list)
    }
}