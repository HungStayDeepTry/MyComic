package hung.deptrai.mycomic.core.network.coverArt

import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtDTO
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CoverArtAPI {
    @GET("cover")
    suspend fun getCoverArtById(
        @Query("ids[]") ids: List<String>
    ): Response<JsonResponse<CoverArtDTO>>
}