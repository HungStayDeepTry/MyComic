package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.network.author.SearchAuthorAPI
import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
import hung.deptrai.mycomic.core.network.statistic.SearchStatisticsAPI
import hung.deptrai.mycomic.feature.search.data.remote.SearchComicAPI
import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.author.AuthorDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.author.AuthorResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.coverArt.CoverArtDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.coverArt.CoverArtResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.statistic.StatisticsResponse
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val api: SearchComicAPI,
    private val api1: SearchAuthorAPI,
    private val api2: CoverArtAPI,
    private val api3: SearchStatisticsAPI
) : SearchDataSource{
    override suspend fun getMangaByTitle(title: String): ResultWrapper<MangaDTO?> {
        return safeApiCall { api.getComicByTitle(title) }
    }

    override suspend fun getAuthorById(authorIds: List<String>): ResultWrapper<AuthorResponse?> {
        return safeApiCall { api1.getAuthorById(authorIds) }
    }

    override suspend fun getCoverArtById(coverArtIds: List<String>): ResultWrapper<CoverArtResponse?> {
        return safeApiCall { api2.getCoverArtById(coverArtIds) }
    }

    override suspend fun getStatisticsByIds(mangaId: List<String>): ResultWrapper<StatisticsResponse?> {
        return safeApiCall { api3.getStatisticsForManga(mangaId) }
    }
}