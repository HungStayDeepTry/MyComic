package hung.deptrai.mycomic.core.data.remote.dto.user

import hung.deptrai.mycomic.core.data.remote.dto.Relationship

data class UserDTO(
    val id: String,
    val type: String,
    val attributes: hung.deptrai.mycomic.core.data.remote.dto.user.UserAttributesDTO,
    val relationships: List<hung.deptrai.mycomic.core.data.remote.dto.Relationship>
)
