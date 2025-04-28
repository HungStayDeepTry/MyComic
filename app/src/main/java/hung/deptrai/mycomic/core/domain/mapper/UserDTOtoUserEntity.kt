package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.domain.model.UserEntity
import hung.deptrai.mycomic.feature.search.data.remote.dto.user.UserDTO

fun UserDTOtoUserEntity(userDTO: UserDTO) : UserEntity{
    val attr = userDTO.attributes
    return UserEntity(
        id = userDTO.id,
        relationships = userDTO.relationships,
        version = attr.version,
        username = attr.username,
        roles = attr.roles
    )
}