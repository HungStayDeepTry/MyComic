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
    suspend fun getMangaByTitle(title: String): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.Attributes>>, DataError.Network>
    suspend fun getAuthorById(authorIds: List<String>): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes>>, DataError.Network>
    suspend fun getCoverArtById(coverArtIds: List<String>): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.coverArt.CoverArtAttributes>>, DataError.Network>
}