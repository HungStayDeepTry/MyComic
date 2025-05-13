package hung.deptrai.mycomic.core.data.dto.scanlationGroup

import hung.deptrai.mycomic.core.data.dto.Relationship

data class ScanlationGroupDTO(
    val id: String,
    val type: String,
    val attributes: ScanlationGroupAttributes,
    val relationships: List<Relationship>
)
