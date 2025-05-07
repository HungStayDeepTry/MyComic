package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
//import hung.deptrai.mycomic.core.domain.model.UserEntity
import hung.deptrai.mycomic.core.data.dto.user.UserAttributesDTO
//import hung.deptrai.mycomic.feature.search.data.dto.user.UserDTO
import hung.deptrai.mycomic.feature.search.domain.model.UserSearch

fun UserDTOtoUserSearch(userDTO: DTOject<UserAttributesDTO>) : UserSearch{
    val attr = userDTO.attributes
    return UserSearch(
        id = userDTO.id,
        name = attr.username,
    )
}