package hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup

import hung.deptrai.mycomic.core.data.remote.dto.Relationship

data class ScanlationGroupDTO(
    val id: String,
    val type: String,
    val attributes: hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes,
    val relationships: List<hung.deptrai.mycomic.core.data.remote.dto.Relationship>
)
