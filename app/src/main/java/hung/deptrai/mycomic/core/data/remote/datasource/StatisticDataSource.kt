package hung.deptrai.mycomic.core.data.remote.datasource

import hung.deptrai.mycomic.core.data.remote.dto.statistic.StatisticsResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface StatisticDataSource {
    suspend fun getStatisticsForMangaByIds(mangaId: List<String>) : Result<hung.deptrai.mycomic.core.data.remote.dto.statistic.StatisticsResponse, DataError.Network>
    suspend fun getStatisticsForChapterByIds(chapterId: List<String>): Result<StatisticsResponse, DataError.Network>
}