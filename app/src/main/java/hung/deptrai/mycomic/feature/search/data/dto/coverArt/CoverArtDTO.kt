package hung.deptrai.mycomic.feature.search.data.dto.coverArt

import hung.deptrai.mycomic.feature.search.data.dto.Relationship

data class CoverArtDTO(
    val id: String,
    val type: String,
    val attributes: hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtAttributes,  // attributes object for cover art
    val relationships: List<hung.deptrai.mycomic.feature.search.data.dto.Relationship> // relationships array for related entities
)
