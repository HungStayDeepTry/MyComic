package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.data.remote.dto.Attributes
import hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes
import hung.deptrai.mycomic.core.data.remote.dto.coverArt.CoverArtAttributes
import hung.deptrai.mycomic.core.data.remote.dto.statistic.StatisticsResponse
import hung.deptrai.mycomic.core.data.remote.dto.tag.TagAttributesDTO
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface SearchComicDataSource {
    suspend fun getMangaByTitle(title: String): Result<JsonResponse<DTOject<Attributes>>, DataError.Network>
    suspend fun getAuthorById(authorIds: List<String>): Result<JsonResponse<DTOject<AuthorAttributes>>, DataError.Network>
    suspend fun getCoverArtById(coverArtIds: List<String>): Result<JsonResponse<DTOject<CoverArtAttributes>>, DataError.Network>
}