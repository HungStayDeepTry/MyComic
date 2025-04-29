package hung.deptrai.mycomic.feature.search.data.dto.author

data class AuthorResponse(
    val result: String,
    val data: List<hung.deptrai.mycomic.feature.search.data.dto.author.AuthorDTO> // key là mangaId
)