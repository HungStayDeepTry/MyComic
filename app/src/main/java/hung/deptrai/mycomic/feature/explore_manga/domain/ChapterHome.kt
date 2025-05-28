package hung.deptrai.mycomic.feature.explore_manga.domain

data class ChapterHome(
    val id: String,
    val title: String?,
    val vol: String?,
    val chapter: String,
    val translatedLang: String,
    val updatedAt: String,
    val commentCount: Int?,
    val scanlationGroup: String?
)
