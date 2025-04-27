package hung.deptrai.mycomic.feature.search.data.remote.dto.user

data class UserAttributesDTO(
    val username: String,
    val roles: List<String>,
    val version: Int
)