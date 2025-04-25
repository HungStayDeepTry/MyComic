package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.domain.model.ScanlationGroupEntity
import hung.deptrai.mycomic.feature.search.data.remote.dto.scanlationGroup.ScanlationGroupDTO

fun ScanlationGrouptoScanlationEntity(
    scanlationGroupDTO: ScanlationGroupDTO
): ScanlationGroupEntity{
    val attr = scanlationGroupDTO.attributes
    return ScanlationGroupEntity(
        id = scanlationGroupDTO.id,
        name = attr.name,
        createdAt = attr.createdAt,
        focusedLanguages = attr.focusedLanguages,
        updatedAt = attr.updatedAt,
        relationships = scanlationGroupDTO.relationships,
        exLicensed = attr.exLicensed ?: false,
        description = (attr.description ?: "").toString(),
        locked = attr.locked,
        inactive = attr.inactive,
        official = attr.official,
        verified = attr.verified,
        twitter = attr.twitter,
        version = attr.version,
        website = attr.website
    )
}