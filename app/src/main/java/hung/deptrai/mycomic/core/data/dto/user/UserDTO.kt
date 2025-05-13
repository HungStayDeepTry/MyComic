package hung.deptrai.mycomic.core.data.dto.user

import hung.deptrai.mycomic.core.data.dto.Relationship

data class UserDTO(
    val id: String,
    val type: String,
    val attributes: UserAttributesDTO,
    val relationships: List<Relationship>
)
