package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.core.network.author.SearchAuthorAPI
import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
import hung.deptrai.mycomic.core.network.statistic.SearchStatisticsAPI
import hung.deptrai.mycomic.feature.search.data.dto.Attributes
import hung.deptrai.mycomic.feature.search.data.remote.SearchComicAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchComicDataSource
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtAttributes
import hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagAttributesDTO
import javax.inject.Inject

class SearchComicDataSourceImpl @Inject constructor(
    private val api: SearchComicAPI,
    private val api1: SearchAuthorAPI,
    private val api2: CoverArtAPI,
    private val api3: SearchStatisticsAPI
) : SearchComicDataSource {
    override suspend fun getMangaByTitle(title: String): ResultWrapper<JsonResponse<DTOject<Attributes>>> {
        return safeApiCall { api.getComicByTitle(title) }
    }

    override suspend fun getAuthorById(authorIds: List<String>): ResultWrapper<JsonResponse<DTOject<AuthorAttributes>>> {
        return safeApiCall { api1.getAuthorById(authorIds) }
    }

    override suspend fun getCoverArtById(coverArtIds: List<String>): ResultWrapper<JsonResponse<DTOject<CoverArtAttributes>>> {
        return safeApiCall { api2.getCoverArtById(coverArtIds) }
    }

    override suspend fun getStatisticsByIds(mangaId: List<String>): ResultWrapper<StatisticsResponse> {
        return safeApiCall { api3.getStatisticsForManga(mangaId) }
    }
    override suspend fun fetchAllTags(): ResultWrapper<JsonResponse<DTOject<TagAttributesDTO>>> {
        return safeApiCall { api.getTags() }
    }
}