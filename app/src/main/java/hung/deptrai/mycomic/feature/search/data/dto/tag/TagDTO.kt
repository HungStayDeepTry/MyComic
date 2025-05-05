package hung.deptrai.mycomic.feature.search.data.dto.tag

import hung.deptrai.mycomic.feature.search.data.dto.Relationship
import kotlinx.serialization.Serializable

@Serializable
data class TagDTO(
    val id: String,
    val type: String,
    val attributes: TagAttributesDTO,
    val relationships: List<Relationship>
)