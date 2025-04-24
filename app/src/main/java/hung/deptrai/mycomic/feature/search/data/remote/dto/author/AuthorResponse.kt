package hung.deptrai.mycomic.feature.search.data.remote.dto.author

data class AuthorResponse(
    val result: String,
    val data: List<AuthorDTO> // key là mangaId
)