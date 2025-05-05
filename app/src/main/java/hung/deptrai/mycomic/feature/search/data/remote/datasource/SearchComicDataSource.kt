package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.Attributes
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtAttributes
import hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse

interface SearchComicDataSource {
    suspend fun getMangaByTitle(title: String): ResultWrapper<JsonResponse<DTOject<Attributes>>>
    suspend fun getAuthorById(authorIds: List<String>): ResultWrapper<JsonResponse<DTOject<AuthorAttributes>>>
    suspend fun getCoverArtById(coverArtIds: List<String>): ResultWrapper<JsonResponse<DTOject<CoverArtAttributes>>>
    suspend fun getStatisticsByIds(mangaId: List<String>) : ResultWrapper<StatisticsResponse>
}