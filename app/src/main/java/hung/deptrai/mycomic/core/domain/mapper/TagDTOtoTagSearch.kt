package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.tag.TagAttributesDTO
import hung.deptrai.mycomic.core.domain.model.Tag

fun TagDTOtoTagSearch(
    tagDTO: hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.tag.TagAttributesDTO>
): Tag {
    return Tag(
        id = tagDTO.id,
        name = tagDTO.attributes.name["en"] ?: "",            // Map trực tiếp trường name (Map<String, String>)
        group = tagDTO.attributes.group
    )
}