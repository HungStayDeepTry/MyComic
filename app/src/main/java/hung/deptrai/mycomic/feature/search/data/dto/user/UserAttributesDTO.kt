package hung.deptrai.mycomic.feature.search.data.dto.user

data class UserAttributesDTO(
    val username: String,
    val roles: List<String>,
    val version: Int
)