package hung.deptrai.mycomic.feature.explore_manga.data.remote.repository

import hung.deptrai.constants.MdConstants
import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.remote.datasource.ArtistDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.AuthorDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.StatisticDataSource
import hung.deptrai.mycomic.core.data.remote.dto.statistic.CommentDTO
import hung.deptrai.mycomic.core.data.remote.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.core.data.remote.dto.statistic.RatingDTO
import hung.deptrai.mycomic.core.data.utils.MdUtil
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.mapper.mangaDTOtoMangaHome
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource.MangaPageDataSource
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MangaPageRepositoryImpl @Inject constructor(
    private val dataSource: MangaPageDataSource,
    private val statisticDs: StatisticDataSource,
    private val artistDataSource: ArtistDataSource,
    private val authorDataSource: AuthorDataSource
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
                    when (val statRes = statisticDs.getStatisticsByIds(mangaIds)) {
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
                    val author = authorDataSource.getAuthorById()
                    when (val statRes = statisticDs.getStatisticsByIds(mangaIds)) {
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
                is Result.Error -> {
                    Result.Error(rs.error)
                }
            }
        }
    }
}