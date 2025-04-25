package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.author.AuthorResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.coverArt.CoverArtResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.statistic.StatisticsResponse

interface SearchComicDataSource {
    suspend fun getMangaByTitle(title: String): ResultWrapper<MangaDTO?>
    suspend fun getAuthorById(authorIds: List<String>): ResultWrapper<AuthorResponse?>
    suspend fun getCoverArtById(coverArtIds: List<String>): ResultWrapper<CoverArtResponse?>
    suspend fun getStatisticsByIds(mangaId: List<String>) : ResultWrapper<StatisticsResponse?>
}