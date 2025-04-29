package hung.deptrai.mycomic.feature.search.data.dto.statistic

data class MangaStatisticDTO(
    val comments: hung.deptrai.mycomic.feature.search.data.dto.statistic.CommentDTO?,
    val follows: Int,
    val rating: hung.deptrai.mycomic.feature.search.data.dto.statistic.RatingDTO
)