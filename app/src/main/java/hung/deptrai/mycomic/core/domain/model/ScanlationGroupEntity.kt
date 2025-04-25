package hung.deptrai.mycomic.core.domain.model

import hung.deptrai.mycomic.feature.search.data.remote.dto.Relationship

data class ScanlationGroupEntity(
    val id: String,
    val name: String,
    val description: String?,
    val website: String?,
    val twitter: String?,
    val focusedLanguages: List<String>,
    val locked: Boolean,
    val official: Boolean,
    val verified: Boolean,
    val inactive: Boolean,
    val exLicensed: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val version: Int,
    val relationships: List<Relationship>
)