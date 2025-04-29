package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.network.author.SearchAuthorByIdAPI
import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
import hung.deptrai.mycomic.core.network.statistic.SearchStatisticsAPI
import hung.deptrai.mycomic.feature.search.data.remote.SearchComicAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchComicDataSource
import hung.deptrai.mycomic.feature.search.data.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorResponse
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtResponse
import hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse
import javax.inject.Inject

class SearchComicDataSourceImpl @Inject constructor(
    private val api: SearchComicAPI,
    private val api1: SearchAuthorByIdAPI,
    private val api2: CoverArtAPI,
    private val api3: SearchStatisticsAPI
) : SearchComicDataSource {
    override suspend fun getMangaByTitle(title: String): ResultWrapper<hung.deptrai.mycomic.feature.search.data.dto.MangaDTO?> {
        return safeApiCall { api.getComicByTitle(title) }
    }

    override suspend fun getAuthorById(authorIds: List<String>): ResultWrapper<hung.deptrai.mycomic.feature.search.data.dto.author.AuthorResponse?> {
        return safeApiCall { api1.getAuthorById(authorIds) }
    }

    override suspend fun getCoverArtById(coverArtIds: List<String>): ResultWrapper<hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtResponse?> {
        return safeApiCall { api2.getCoverArtById(coverArtIds) }
    }

    override suspend fun getStatisticsByIds(mangaId: List<String>): ResultWrapper<hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse?> {
        return safeApiCall { api3.getStatisticsForManga(mangaId) }
    }
}