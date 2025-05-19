package hung.deptrai.mycomic.feature.explore_manga.domain

import hung.deptrai.mycomic.core.domain.model.Tag

data class MangaHome(
    val id: String,
    val title: String?,
    val authorName: String,
    val artist: String?,
    val coverArt: String,
    val originalLang: String?,
    val commentCount: Int?,
    val tags: List<Tag>,
    val lastUpdatedChapter: ChapterHome
)

