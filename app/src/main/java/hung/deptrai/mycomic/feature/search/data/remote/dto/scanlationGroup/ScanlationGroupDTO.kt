package hung.deptrai.mycomic.feature.search.data.remote.dto.scanlationGroup

import hung.deptrai.mycomic.feature.search.data.remote.dto.Relationship

data class ScanlationGroupDTO(
    val id: String,
    val type: String,
    val attributes: ScanlationGroupAttributes,
    val relationships: List<Relationship>
)
