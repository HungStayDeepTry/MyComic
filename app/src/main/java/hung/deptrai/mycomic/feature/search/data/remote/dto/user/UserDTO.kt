package hung.deptrai.mycomic.feature.search.data.remote.dto.user

import hung.deptrai.mycomic.feature.search.data.remote.dto.Relationship

data class UserDTO(
    val id: String,
    val type: String,
    val attributes: UserAttributesDTO,
    val relationships: List<Relationship>
)
