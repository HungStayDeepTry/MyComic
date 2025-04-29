package hung.deptrai.mycomic.feature.search.data.dto.user

import hung.deptrai.mycomic.feature.search.data.dto.Relationship

data class UserDTO(
    val id: String,
    val type: String,
    val attributes: hung.deptrai.mycomic.feature.search.data.dto.user.UserAttributesDTO,
    val relationships: List<hung.deptrai.mycomic.feature.search.data.dto.Relationship>
)
