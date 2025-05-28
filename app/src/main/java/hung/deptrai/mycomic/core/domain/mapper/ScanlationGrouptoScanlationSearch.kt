package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes
//import hung.deptrai.mycomic.core.domain.model.ScanlationGroupEntity
import hung.deptrai.mycomic.core.data.remote.dto.user.UserAttributesDTO
import hung.deptrai.mycomic.feature.search.domain.model.ScanlationGroupSearch

fun ScanlationGrouptoScanlationSearch(
    scanlationGroupDTO: hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes>,
    userDTO: List<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.user.UserAttributesDTO>>
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