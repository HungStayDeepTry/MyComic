package hung.deptrai.mycomic.core.data.dto.statistic

data class MangaStatisticDTO(
    val comments: CommentDTO?,
    val follows: Int,
    val rating: RatingDTO
)