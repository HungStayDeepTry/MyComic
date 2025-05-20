package hung.deptrai.mycomic.feature.explore_manga.data.remote.repository

import androidx.compose.material.ripple.R
import hung.deptrai.constants.MdConstants
import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.remote.datasource.ArtistDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.AuthorDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.ChapterDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.ScanlationGroupDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.StatisticDataSource
import hung.deptrai.mycomic.core.data.remote.dto.statistic.CommentDTO
import hung.deptrai.mycomic.core.data.remote.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.core.data.remote.dto.statistic.RatingDTO
import hung.deptrai.mycomic.core.data.utils.MdUtil
import hung.deptrai.mycomic.core.data.utils.chapterDTOtoChapterEntity
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.mapper.mangaDTOtoMangaHome
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.explore_manga.data.local.HomeLocalDataSource
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource.MangaPageDataSource
import hung.deptrai.mycomic.feature.explore_manga.domain.ChapterHome
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaPageRepository
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MangaPageRepositoryImpl @Inject constructor(
    private val dataSource: MangaPageDataSource,
    private val statisticDs: StatisticDataSource,
    private val artistDataSource: ArtistDataSource,
    private val authorDataSource: AuthorDataSource,
    private val chapterDataSource: ChapterDataSource,
    private val scanlationGroupDataSource: ScanlationGroupDataSource,
    private val localDataSource: HomeLocalDataSource
) : MangaPageRepository{
    override suspend fun fetchMangaPageInfo(): List<Result<List<Any>, DataError.Network>> {

    }

    private suspend fun recentlyAdded(page: Int): Result<List<MangaHome>, DataError.Network> {
        return withContext(Dispatchers.IO) {
            val queryParameters = mutableMapOf<String, Any>()
            queryParameters[MdConstants.SearchParameters.limit] = MdConstants.Limits.manga
            queryParameters[MdConstants.SearchParameters.offset] = MdUtil.getMangaListOffset(page)

            when (val rs = dataSource.recentlyAdded(ProxyRetrofitQueryMap(queryParameters))) {
                is Result.Success -> {
                    val res = rs.data.data
                    if (res.isEmpty()) return@withContext Result.Success(emptyList())

                    val coverArtMap = res.associate { dto ->
                        val coverArt = dto.relationships.firstOrNull {
                            it.type == "cover_art" && it.related != null
                        }?.related
                        dto.id to coverArt
                    }

                    val mangaIds = res.map { it.id }
                    when (val statRes = statisticDs.getStatisticsForMangaByIds(mangaIds)) {
                        is Result.Success -> {
                            val data = res.map {
                                mangaDTOtoMangaHome(
                                    mangaDTO = it,
                                    coverArtDTO = coverArtMap[it.id]
                                )
                            }
                            Result.Success(data)
                        }

                        is Result.Error -> Result.Error(statRes.error)
                    }
                }

                is Result.Error -> Result.Error(rs.error)
            }
        }
    }

    private suspend fun popularNewTitles(page: Int): Result<List<MangaHome>, DataError.Network>{
        return withContext(Dispatchers.IO){
            val queryParameters = mutableMapOf<String, Any>()
            queryParameters[MdConstants.SearchParameters.limit] = MdConstants.Limits.manga
            queryParameters[MdConstants.SearchParameters.offset] = MdUtil.getMangaListOffset(page)

            when (val rs = dataSource.popularNewTitles(ProxyRetrofitQueryMap(queryParameters))){
                is Result.Success -> {
                    val res = rs.data.data
                    if (res.isEmpty()) return@withContext Result.Success(emptyList())

                    val coverArtMap = res.associate { dto ->
                        val coverArt = dto.relationships.firstOrNull {
                            it.type == "cover_art" && it.related != null
                        }?.related
                        dto.id to coverArt
                    }

                    val mangaIds = res.map { it.id }

                    val authorIds = res
                        .flatMap { it.relationships.filter { it.type == "author" }.map { it.id } }
                        .distinct()

                    val artistIds = res
                        .flatMap { it.relationships.filter { it.type == "artist" }.map { it.id } }
                        .distinct()

                    val author = authorDataSource.getAuthorById(authorIds)
                    val artist = artistDataSource.getArtistById(artistIds)

                    if(
                        author is Result.Success &&
                        artist is Result.Success
                    ){
                        val authorMap = author.data.data.associateBy { it.id }
                        val artistMap = artist.data.data.associateBy { it.id }

                        val comics = res.mapNotNull { dto ->
                            val authors = dto.relationships
                                .filter { it.type == "author" }
                                .mapNotNull { authorMap[it.id] }

                            val coverArt = dto.relationships
                                .firstOrNull { it.type == "cover_art" }
                                ?.id
                                ?.let { coverArtMap[it] }

                            val artists = dto.relationships
                                .filter {
                                    it.type == "artist"
                                }
                                .mapNotNull {
                                    artistMap[it.id]
                                }

                            if (coverArt != null) {
                                mangaDTOtoMangaHome(dto, coverArt, authors, artists)
                            } else null
                        }
                        Result.Success(comics)
                    }
                    else{
                        // Ưu tiên lỗi có trước
                        listOf(author, artist)
                            .firstOrNull()
                            ?.let { return@let it as Result<List<MangaHome>, DataError.Network> }

                        Result.Error(DataError.Network.UNKNOWN)
                    }
                }
                is Result.Error -> {
                    Result.Error(rs.error)
                }
            }
        }
    }

    private suspend fun getLatestChapterUpdated():
            Result<List<ChapterEntity>, DataError.Network> {
        return withContext(Dispatchers.IO) {
            when (val res = chapterDataSource.getLatestUpdatedChapter()) {
                is Result.Success -> {
                    val rs = res.data.data
                    val chapterIds = rs.map { it.id }
                    val scanlationGroupIds = rs.mapNotNull {
                        it.relationships.firstOrNull{
                            it.type == "scanlation_group"
                        }?.id
                    }
                    val statisticRes = statisticDs.getStatisticsForChapterByIds(chapterIds)
                    val scanGroupRes = scanlationGroupDataSource.getScanlationGroup(scanlationGroupIds)
                    if(statisticRes is Result.Success && scanGroupRes is Result.Success) {
                            val statMap = statisticRes.data.statistics
                            val scanMap = scanGroupRes.data.data.associateBy { it.id }
                            val chapters = rs.mapNotNull { dto ->
                                val scanGroups = dto.relationships
                                    .firstOrNull { it.type == "scanlation_group" }
                                    ?.id
                                    ?.let { scanMap[it] }
                                val statistic = statMap[dto.id]
                                if(statistic != null && scanGroups != null){
                                    chapterDTOtoChapterEntity(dto, statistic, scanGroups)
                                } else{
                                    null
                                }
                            }
                            Result.Success(chapters)
                    } else{
                        listOf(scanGroupRes, scanGroupRes)
                            .firstOrNull()
                            ?.let { return@let it as Result<List<MangaHome>, DataError.Network> }

                        Result.Error(DataError.Network.UNKNOWN)
                    }
                }
                is Result.Error -> Result.Error(res.error)
            }
        }
    }

    suspend fun refreshLatestChaptersFromRemote() {
        when (val result = getLatestChapterUpdated()) {
            is Result.Success -> {
                localDataSource.upsertChapter(result.data)
            }

            is Result.Error -> {
                // Tùy chọn xử lý lỗi, có thể throw nếu muốn

            }
        }
    }

    fun getLatestChaptersForHome(): Flow<List<ChapterHome>>{
        return localDataSource.getLatestChapters()
            .map {
                ent -> ent.map {
                    it.
            }
            }
    }


    override fun observeMangaByType(type: CustomType): Flow<List<HomeMangaEntity>>{
        return when(type){
            CustomType.POPULAR_NEW_TITLES -> localDataSource.getPopularNewTitles()
            CustomType.RECENTLY_ADDED -> localDataSource.getRecentlyAdded(Int.MAX_VALUE)
            CustomType.STAFF_PICKS -> localDataSource.getStaffPicks(Int.MAX_VALUE)
            CustomType.SELF_PUBLISHED -> localDataSource.getSelfPublished(Int.MAX_VALUE)
            CustomType.FEATURE -> localDataSource.getFeature(Int.MAX_VALUE)
            CustomType.SEASONAL -> localDataSource.getSeasonal(Int.MAX_VALUE)
            CustomType.LATEST_UPDATES -> localDataSource.getMangaByIds()
        }
    }
}