package hung.deptrai.mycomic.core.data.dto

data class ChapterDTO(
    val volume : String?,
    val chapter: String?,
    val title: String?,
    val translatedLanguage: String?,
    val externalUrl: String?,
    val publishAt : String?,
    val readableAt : String?,
    val createdAt : String?,
    val updatedAt : String?,
    val pages: Int,
    val version: Int
)