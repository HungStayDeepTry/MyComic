package hung.deptrai.mycomic.core.network.coverArt

import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.data.remote.dto.coverArt.CoverArtAttributes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoverArtAPI {
    @GET("cover")
    suspend fun getCoverArtById(
        @Query("ids[]") ids: List<String>
    ): Response<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.coverArt.CoverArtAttributes>>>
}