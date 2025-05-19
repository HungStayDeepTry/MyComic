package hung.deptrai.mycomic.core.data.remote.dto.coverArt

import hung.deptrai.mycomic.core.data.remote.dto.Relationship

data class CoverArtDTO(
    val id: String,
    val type: String,
    val attributes: hung.deptrai.mycomic.core.data.remote.dto.coverArt.CoverArtAttributes,  // attributes object for cover art
    val relationships: List<hung.deptrai.mycomic.core.data.remote.dto.Relationship> // relationships array for related entities
)
