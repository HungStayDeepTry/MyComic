package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.JsonFewerResponse
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorDTO
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorResponse
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtDTO
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtResponse
import hung.deptrai.mycomic.feature.search.data.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse

interface SearchComicDataSource {
    suspend fun getMangaByTitle(title: String): ResultWrapper<JsonResponse<MangaDTO>>
    suspend fun getAuthorById(authorIds: List<String>): ResultWrapper<JsonResponse<AuthorDTO>>
    suspend fun getCoverArtById(coverArtIds: List<String>): ResultWrapper<JsonResponse<CoverArtDTO>>
    suspend fun getStatisticsByIds(mangaId: List<String>) : ResultWrapper<StatisticsResponse>
}