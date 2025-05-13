package hung.deptrai.mycomic.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Relationship(
    val id: String,
    val related: String?=null,
    val type: String
)