package hung.deptrai.mycomic.feature.search.data.remote.dto.coverArt

data class CoverArtResponse(
    val result: String,
    val data: List<CoverArtDTO> // key là mangaId
)