package hung.deptrai.mycomic.core.data.dto

import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtDTO

data class JsonFewerResponse<T> (
    val result: String,
    val data: List<T>
)