package hung.deptrai.mycomic.core.data.remote.dto.statistic

data class StatisticsResponse(
    val result: String,
    val statistics: Map<String, hung.deptrai.mycomic.core.data.remote.dto.statistic.MangaStatisticDTO> // key là mangaId
)