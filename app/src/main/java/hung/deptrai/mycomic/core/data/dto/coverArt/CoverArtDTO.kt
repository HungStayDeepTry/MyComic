package hung.deptrai.mycomic.core.data.dto.coverArt

import hung.deptrai.mycomic.core.data.dto.Relationship

data class CoverArtDTO(
    val id: String,
    val type: String,
    val attributes: CoverArtAttributes,  // attributes object for cover art
    val relationships: List<Relationship> // relationships array for related entities
)
