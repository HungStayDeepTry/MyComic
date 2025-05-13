package hung.deptrai.mycomic.core.network.coverArt

import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.data.dto.coverArt.CoverArtAttributes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoverArtAPI {
    @GET("cover")
    suspend fun getCoverArtById(
        @Query("ids[]") ids: List<String>
    ): Response<JsonResponse<DTOject<CoverArtAttributes>>>
}