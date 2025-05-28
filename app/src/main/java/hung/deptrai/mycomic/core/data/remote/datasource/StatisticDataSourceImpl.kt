package hung.deptrai.mycomic.core.data.remote.datasource

import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.data.remote.dto.statistic.StatisticsResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.statistic.SearchStatisticsAPI
import javax.inject.Inject

class StatisticDataSourceImpl @Inject constructor(
    private val api: SearchStatisticsAPI
) : StatisticDataSource{
    override suspend fun getStatisticsForMangaByIds(mangaId: List<String>): Result<StatisticsResponse, DataError.Network> {
        return safeApiCall { api.getStatisticsForManga(mangaId) }
    }

    override suspend fun getStatisticsForChapterByIds(chapterId: List<String>): Result<StatisticsResponse, DataError.Network> {
        return safeApiCall {
            api.getStatisticsForChapter(chapterId)
        }
    }
}

//override suspend fun getStatisticsByIds(mangaId: List<String>): Result<hung.deptrai.mycomic.core.data.remote.dto.statistic.StatisticsResponse, DataError.Network> {
//    return safeApiCall { api3.getStatisticsForManga(mangaId) }
//}