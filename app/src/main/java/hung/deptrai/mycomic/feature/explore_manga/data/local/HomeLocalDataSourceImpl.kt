package hung.deptrai.mycomic.feature.explore_manga.data.local

import hung.deptrai.mycomic.feature.explore_manga.data.local.dao.HomeMangaDao
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.MangaTagCrossRef
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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

    override fun getTagsByMangaIds(mangaIds: List<String>): Flow<Map<String, List<TagEntity>>> {
        return combine(
            dao.getMangaTagCrossRefsByMangaIds(mangaIds),
            dao.getAllTagsFlow()
        ) { crossRefs, tags ->

            val tagMap = tags.associateBy { it.id }

            crossRefs.groupBy { it.mangaId }
                .mapValues { entry ->
                    entry.value.mapNotNull { crossRef -> tagMap[crossRef.tagId] }
                }
        }
    }

    override fun getLatestChapters(): Flow<List<ChapterEntity>> {
        return dao.getLatestUpdated()
    }

    override suspend fun upsertByType(list: List<HomeMangaEntity>, type: CustomType) {
        dao.clearMangaByType(type)
        dao.upsertAll(list)
    }

    override suspend fun upsertChapter(list: List<ChapterEntity>) {
        dao.clearChapterByType()
        dao.upsertAllChapters(list)
    }

    override suspend fun insertTags(tags: List<TagEntity>) {
        dao.insertTags(tags)
    }

    override suspend fun insertMangaTagCrossRefs(refs: List<MangaTagCrossRef>, mangaIds: List<String>) {
        dao.clearMangaTagCrossRefsByMangaIds(mangaIds)
        dao.insertMangaTagCrossRefs(refs)
    }

    override suspend fun upsertAllMangas(list: List<HomeMangaEntity>, customType: CustomType) {
        dao.clearMangaByType(customType)
        dao.upsertAll(list)
    }
}