package hung.deptrai.mycomic.feature.search.data.dto.statistic

data class StatisticsResponse(
    val result: String,
    val statistics: Map<String, hung.deptrai.mycomic.feature.search.data.dto.statistic.MangaStatisticDTO> // key là mangaId
)