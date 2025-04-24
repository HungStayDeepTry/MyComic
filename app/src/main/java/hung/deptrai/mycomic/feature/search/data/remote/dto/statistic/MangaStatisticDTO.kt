package hung.deptrai.mycomic.feature.search.data.remote.dto.statistic

data class MangaStatisticDTO(
    val comments: CommentDTO?,
    val follows: Int,
    val rating: RatingDTO
)