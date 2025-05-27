package hung.deptrai.mycomic.feature.explore_manga.domain

import hung.deptrai.mycomic.core.domain.model.Tag

data class MangaHome(
    val id: String,
    val title: String?,
    val authorName: String,
    val artist: String?,
    val coverArt: String,
    val originalLang: String?,
    val tags: List<Tag>,
    val lastUpdatedChapter: ChapterHome?,
    val customType: Type,
    val contentRating: String?
)

enum class Type {
        POPULAR_NEW_TITLES,
        LATEST_UPDATES,
        STAFF_PICKS,
        SELF_PUBLISHED,
        FEATURE,
        SEASONAL,
        RECENTLY_ADDED
}