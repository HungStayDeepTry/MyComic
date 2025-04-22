package hung.deptrai.mycomic.feature.search.data.remote.dto

data class MangaDTO(
    val `data`: List<Data>,
    val limit: Int,
    val offset: Int,
    val response: String,
    val result: String,
    val total: Int
)