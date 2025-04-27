package hung.deptrai.mycomic.core.domain.model

import hung.deptrai.mycomic.feature.search.data.remote.dto.Relationship

data class UserEntity(
    val id: String,
    val username: String,
    val roles: List<String>,
    val version: Int,
    val relationships: List<Relationship>?
)