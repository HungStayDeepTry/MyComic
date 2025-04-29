package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.domain.model.TagEntity
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagDTO

fun TagDTOtoTagEntity(
    tagDTO: TagDTO
): TagEntity{
    return TagEntity(
        id = tagDTO.id,
        name = tagDTO.attributes.name,            // Map trực tiếp trường name (Map<String, String>)
        group = tagDTO.attributes.group,
        description = tagDTO.attributes.description ?: emptyMap()
    )
}