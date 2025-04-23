package hung.deptrai.mycomic.feature.search.data.remote.dto.author

import hung.deptrai.mycomic.feature.search.data.remote.dto.Relationship

data class AuthorDTO(
    val id: String,
    val type: String,
    val attributes: AuthorAttributes,  // attributes object for author
    val relationships: List<Relationship> // relationships array for related entities
)
