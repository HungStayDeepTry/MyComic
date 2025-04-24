package hung.deptrai.mycomic.feature.search.data.remote.dto.statistic

data class StatisticsResponse(
    val result: String,
    val statistics: Map<String, MangaStatisticDTO> // key là mangaId
)