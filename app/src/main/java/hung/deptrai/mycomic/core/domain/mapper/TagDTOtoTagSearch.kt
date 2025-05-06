package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagAttributesDTO
import hung.deptrai.mycomic.feature.search.domain.model.TagSearch

fun TagDTOtoTagSearch(
    tagDTO: DTOject<TagAttributesDTO>
): TagSearch{
    return TagSearch(
        id = tagDTO.id,
        name = tagDTO.attributes.name["en"] ?: "",            // Map trực tiếp trường name (Map<String, String>)
        group = tagDTO.attributes.group
    )
}