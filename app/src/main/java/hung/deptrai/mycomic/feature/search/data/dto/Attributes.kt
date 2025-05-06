package hung.deptrai.mycomic.feature.search.data.dto

data class Attributes(
    val altTitles: List<hung.deptrai.mycomic.feature.search.data.dto.AltTitle>,
    val availableTranslatedLanguages: List<String>,
    val chapterNumbersResetOnNewVolume: Boolean,
    val contentRating: String,
    val createdAt: String,
    val description: hung.deptrai.mycomic.feature.search.data.dto.Description,
    val isLocked: Boolean,
    val lastChapter: String?,
    val lastVolume: String,
    val latestUploadedChapter: String,
    val links: hung.deptrai.mycomic.feature.search.data.dto.Links,
    val originalLanguage: String,
    val publicationDemographic: String,
    val state: String,
    val status: String,
    val tags: List<hung.deptrai.mycomic.feature.search.data.dto.Tag>,
    val title: hung.deptrai.mycomic.feature.search.data.dto.Title,
    val updatedAt: String,
    val version: Int,
    val year: Int
)