package hung.deptrai.mycomic.feature.search.data.dto.coverArt

data class CoverArtResponse(
    val result: String,
    val data: List<hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtDTO> // key là mangaId
)