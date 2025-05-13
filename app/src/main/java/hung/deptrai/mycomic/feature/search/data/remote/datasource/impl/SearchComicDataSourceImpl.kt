package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.author.SearchAuthorAPI
import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
import hung.deptrai.mycomic.core.network.statistic.SearchStatisticsAPI
import hung.deptrai.mycomic.core.data.dto.Attributes
import hung.deptrai.mycomic.core.network.MangaAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchComicDataSource
import hung.deptrai.mycomic.core.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.core.data.dto.coverArt.CoverArtAttributes
import hung.deptrai.mycomic.core.data.dto.statistic.StatisticsResponse
import hung.deptrai.mycomic.core.data.dto.tag.TagAttributesDTO
import javax.inject.Inject

class SearchComicDataSourceImpl @Inject constructor(
    private val api: MangaAPI,
    private val api1: SearchAuthorAPI,
    private val api2: CoverArtAPI,
    private val api3: SearchStatisticsAPI
) : SearchComicDataSource {
    override suspend fun getMangaByTitle(title: String): Result<JsonResponse<DTOject<Attributes>>, DataError.Network> {
        return safeApiCall { api.getComicByTitle(title) }
    }

    override suspend fun getAuthorById(authorIds: List<String>): Result<JsonResponse<DTOject<AuthorAttributes>>, DataError.Network> {
        return safeApiCall { api1.getAuthorById(authorIds) }
    }

    override suspend fun getCoverArtById(coverArtIds: List<String>): Result<JsonResponse<DTOject<CoverArtAttributes>>, DataError.Network> {
        return safeApiCall { api2.getCoverArtById(coverArtIds) }
    }

    override suspend fun getStatisticsByIds(mangaId: List<String>): Result<StatisticsResponse, DataError.Network> {
        return safeApiCall { api3.getStatisticsForManga(mangaId) }
    }
}