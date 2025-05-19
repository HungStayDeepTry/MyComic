package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
//import hung.deptrai.mycomic.core.domain.model.UserEntity
import hung.deptrai.mycomic.core.data.remote.dto.user.UserAttributesDTO
//import hung.deptrai.mycomic.feature.search.data.dto.user.UserDTO
import hung.deptrai.mycomic.feature.search.domain.model.UserSearch

fun UserDTOtoUserSearch(userDTO: hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.user.UserAttributesDTO>) : UserSearch{
    val attr = userDTO.attributes
    return UserSearch(
        id = userDTO.id,
        name = attr.username,
    )
}