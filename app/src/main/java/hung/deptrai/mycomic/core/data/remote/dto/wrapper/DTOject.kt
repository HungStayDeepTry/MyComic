package hung.deptrai.mycomic.core.data.remote.dto.wrapper

import hung.deptrai.mycomic.core.data.remote.dto.Relationship
import hung.deptrai.mycomic.core.data.remote.dto.RelationshipDTO
import kotlinx.serialization.Serializable

@Serializable
data class DTOject<T>(
    val attributes: T,
    val id: String,
    val relationships: List<Relationship>,
    val type: String
)

@Serializable
data class DTOject1<T>(
    val attributes: T,
    val id: String,
    val relationships: List<RelationshipDTO>,
    val type: String
)