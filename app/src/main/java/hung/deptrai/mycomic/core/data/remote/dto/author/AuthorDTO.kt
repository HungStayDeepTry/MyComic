package hung.deptrai.mycomic.core.data.remote.dto.author

import hung.deptrai.mycomic.core.data.remote.dto.Relationship

data class AuthorDTO(
    val id: String,
    val type: String,
    val attributes: hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes,  // attributes object for author
    val relationships: List<hung.deptrai.mycomic.core.data.remote.dto.Relationship> // relationships array for related entities
)
