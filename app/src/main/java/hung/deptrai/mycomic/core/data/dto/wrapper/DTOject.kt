package hung.deptrai.mycomic.core.data.dto.wrapper

import hung.deptrai.mycomic.core.data.dto.Relationship
import kotlinx.serialization.Serializable

@Serializable
data class DTOject<T>(
    val attributes: T,
    val id: String,
    val relationships: List<Relationship>,
    val type: String
)