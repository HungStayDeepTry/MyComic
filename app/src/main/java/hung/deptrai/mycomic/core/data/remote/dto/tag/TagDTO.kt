package hung.deptrai.mycomic.core.data.remote.dto.tag

import hung.deptrai.mycomic.core.data.remote.dto.Relationship
import kotlinx.serialization.Serializable

@Serializable
data class TagDTO(
    val id: String,
    val type: String,
    val attributes: hung.deptrai.mycomic.core.data.remote.dto.tag.TagAttributesDTO,
    val relationships: List<hung.deptrai.mycomic.core.data.remote.dto.Relationship>
)