package hung.deptrai.mycomic.feature.search.data.dto.author

import hung.deptrai.mycomic.feature.search.data.dto.Relationship

data class AuthorDTO(
    val id: String,
    val type: String,
    val attributes: hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes,  // attributes object for author
    val relationships: List<hung.deptrai.mycomic.feature.search.data.dto.Relationship> // relationships array for related entities
)
