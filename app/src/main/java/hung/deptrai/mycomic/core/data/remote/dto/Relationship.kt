package hung.deptrai.mycomic.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Relationship(
    val id: String,
    val related: String? = null,
    val type: String
)

@Serializable
data class RelationshipDTO(
    val id: String,
    val attributes: IncludesAttributesDto? = null,
    val type: String
)

@Serializable
data class IncludesAttributesDto(
    val mangaID: String? = null,
    val fileName: String? = null,
    val description: String? = null,
    val locale: String? = null,
    val volume: String? = null,
)