package hung.deptrai.mycomic.core.data.dto.statistic

data class StatisticsResponse(
    val result: String,
    val statistics: Map<String, MangaStatisticDTO> // key là mangaId
)