package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.scanlationGroup.ScanlationGroupAttributes
//import hung.deptrai.mycomic.core.domain.model.ScanlationGroupEntity
import hung.deptrai.mycomic.core.data.dto.user.UserAttributesDTO
import hung.deptrai.mycomic.feature.search.domain.model.ScanlationGroupSearch

fun ScanlationGrouptoScanlationSearch(
    scanlationGroupDTO: DTOject<ScanlationGroupAttributes>,
    userDTO: List<DTOject<UserAttributesDTO>>
): ScanlationGroupSearch{
    val attr = scanlationGroupDTO.attributes
    val leaderIds = scanlationGroupDTO.relationships
        .filter { it.type == "leader" }
        .map { it.id }

    // Match id với userDTO để lấy tên
    val leaders = userDTO
        .filter { it.id in leaderIds }
        .map { it.attributes.username }
    return ScanlationGroupSearch(
        id = scanlationGroupDTO.id,
        name = attr.name,
        focusedLanguages = attr.focusedLanguages,
        isVerified = attr.verified,
        isOfficial = attr.official,
        leaderName = leaders
    )
}