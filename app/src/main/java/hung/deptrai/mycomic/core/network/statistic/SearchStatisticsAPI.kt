package hung.deptrai.mycomic.core.network.statistic

import hung.deptrai.mycomic.core.data.remote.dto.statistic.StatisticsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchStatisticsAPI {
    @GET("statistics/manga")
    suspend fun getStatisticsForManga(
        @Query("manga[]") mangaIds: List<String>
    ): Response<StatisticsResponse>

    @GET("statistics/chapter")
    suspend fun getStatisticsForChapter(
        @Query("chapter[]") mangaIds: List<String>
    ): Response<StatisticsResponse>
}