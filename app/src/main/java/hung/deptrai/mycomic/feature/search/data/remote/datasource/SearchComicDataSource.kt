package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.Attributes
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtAttributes
import hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagAttributesDTO
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface SearchComicDataSource {
    suspend fun getMangaByTitle(title: String): Result<JsonResponse<DTOject<Attributes>>, DataError.Network>
    suspend fun getAuthorById(authorIds: List<String>): Result<JsonResponse<DTOject<AuthorAttributes>>, DataError.Network>
    suspend fun getCoverArtById(coverArtIds: List<String>): Result<JsonResponse<DTOject<CoverArtAttributes>>, DataError.Network>
    suspend fun getStatisticsByIds(mangaId: List<String>) : Result<StatisticsResponse, DataError.Network>
    suspend fun fetchAllTags(): Result<JsonResponse<DTOject<TagAttributesDTO>>, DataError.Network>
}