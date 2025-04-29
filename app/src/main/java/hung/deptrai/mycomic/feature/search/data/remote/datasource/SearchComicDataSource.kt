package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.feature.search.data.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorResponse
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtResponse
import hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse

interface SearchComicDataSource {
    suspend fun getMangaByTitle(title: String): ResultWrapper<hung.deptrai.mycomic.feature.search.data.dto.MangaDTO?>
    suspend fun getAuthorById(authorIds: List<String>): ResultWrapper<hung.deptrai.mycomic.feature.search.data.dto.author.AuthorResponse?>
    suspend fun getCoverArtById(coverArtIds: List<String>): ResultWrapper<hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtResponse?>
    suspend fun getStatisticsByIds(mangaId: List<String>) : ResultWrapper<hung.deptrai.mycomic.feature.search.data.dto.statistic.StatisticsResponse?>
}