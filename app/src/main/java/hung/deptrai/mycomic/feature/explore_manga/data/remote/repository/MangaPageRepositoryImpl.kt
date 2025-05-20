package hung.deptrai.mycomic.feature.explore_manga.data.remote.repository

import androidx.compose.material.ripple.R
import hung.deptrai.constants.MdConstants
import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.remote.datasource.ArtistDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.AuthorDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.ChapterDataSource
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
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaPageRepository
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MangaPageRepositoryImpl @Inject constructor(
    private val dataSource: MangaPageDataSource,
    private val statisticDs: StatisticDataSource,
    private val artistDataSource: ArtistDataSource,
    private val authorDataSource: AuthorDataSource,
    private val chapterDataSource: ChapterDataSource,
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
                            val statMap = statRes.data.statistics
                            val data = res.map {
                                val stat = statMap[it.id]
                                mangaDTOtoMangaHome(
                                    mangaDTO = it,
                                    statisticDTO = stat ?: MangaStatisticDTO(
                                        CommentDTO(repliesCount = 0, threadId = 0),
                                        follows = 0,
                                        RatingDTO(average = 0.0, bayesian = 0.0)
                                    ),
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
                    val statRes = statisticDs.getStatisticsForMangaByIds(mangaIds)

                    if(
                        author is Result.Success &&
                        artist is Result.Success &&
                        statRes is Result.Success
                    ){
                        val authorMap = author.data.data.associateBy { it.id }
                        val artistMap = artist.data.data.associateBy { it.id }
                        val statMap = statRes.data.statistics

                        val comics = res.mapNotNull { dto ->
                            val authors = dto.relationships
                                .filter { it.type == "author" }
                                .mapNotNull { authorMap[it.id] }

                            val coverArt = dto.relationships
                                .firstOrNull { it.type == "cover_art" }
                                ?.id
                                ?.let { coverArtMap[it] }

                            val stat = statMap[dto.id]

                            val artists = dto.relationships
                                .filter {
                                    it.type == "artist"
                                }
                                .mapNotNull {
                                    artistMap[it.id]
                                }

                            if (coverArt != null && stat != null) {
                                mangaDTOtoMangaHome(dto, coverArt, authors, artists, stat)
                            } else null
                        }
                        Result.Success(comics)
                    }
                    else{
                        // Ưu tiên lỗi có trước
                        listOf(author, artist, statRes)
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

                    when (val statisticRes = statisticDs.getStatisticsForChapterByIds(chapterIds)) {
                        is Result.Success -> {
                            val statMap = statisticRes.data.statistics
                            val chapters = rs.mapNotNull { dto ->
                                statMap[dto.id]?.let { stat ->
                                    chapterDTOtoChapterEntity(dto, stat)
                                }
                            }
                            Result.Success(chapters)
                        }

                        is Result.Error -> Result.Error(statisticRes.error)
                    }
                }
                is Result.Error -> Result.Error(res.error)
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