package hung.deptrai.mycomic.feature.search.data.dto

data class MangaDTO(
    val `data`: List<hung.deptrai.mycomic.feature.search.data.dto.Data>,
    val limit: Int,
    val offset: Int,
    val response: String,
    val result: String,
    val total: Int
)