package hung.deptrai.mycomic.core.data.remote.dto.statistic

data class MangaStatisticDTO(
    val comments: hung.deptrai.mycomic.core.data.remote.dto.statistic.CommentDTO?,
    val follows: Int,
    val rating: hung.deptrai.mycomic.core.data.remote.dto.statistic.RatingDTO
)

data class ChapterStatisticDTO(
    val comments: CommentDTO?
)