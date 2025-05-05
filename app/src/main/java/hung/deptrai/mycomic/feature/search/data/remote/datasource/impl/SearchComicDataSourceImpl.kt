package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.JsonFewerResponse
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.core.network.author.SearchAuthorByIdAPI
import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
import hung.deptrai.mycomic.core.network.statistic.SearchStatisticsAPI
import hung.deptrai.mycomic.feature.search.data.remote.SearchComicAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchComicDataSource
import hung.deptrai.mycomic.feature.search.data.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorDTO
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorResponse
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtDTO
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtResponse
import hung.deptrai.mycomic.feature.search.data.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse
import javax.inject.Inject

class SearchComicDataSourceImpl @Inject constructor(
    private val api: SearchComicAPI,
    private val api1: SearchAuthorByIdAPI,
    private val api2: CoverArtAPI,
    private val api3: SearchStatisticsAPI
) : SearchComicDataSource {
    override suspend fun getMangaByTitle(title: String): ResultWrapper<JsonResponse<MangaDTO>> {
        return safeApiCall { api.getComicByTitle(title) }
    }

    override suspend fun getAuthorById(authorIds: List<String>): ResultWrapper<JsonResponse<AuthorDTO>> {
        return safeApiCall { api1.getAuthorById(authorIds) }
    }

    override suspend fun getCoverArtById(coverArtIds: List<String>): ResultWrapper<JsonResponse<CoverArtDTO>> {
        return safeApiCall { api2.getCoverArtById(coverArtIds) }
    }

    override suspend fun getStatisticsByIds(mangaId: List<String>): ResultWrapper<StatisticsResponse> {
        return safeApiCall { api3.getStatisticsForManga(mangaId) }
    }
}