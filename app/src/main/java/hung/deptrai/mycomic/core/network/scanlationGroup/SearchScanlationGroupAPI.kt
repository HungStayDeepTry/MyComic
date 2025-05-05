package hung.deptrai.mycomic.core.network.scanlationGroup

import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.scanlationGroup.ScanlationGroupDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchScanlationGroupAPI {
    @GET("group")
    suspend fun getScanlationGroupByTitle(
        @Query("name") name: String,
        @Query("limit") limit: Int = 20
    ): Response<JsonResponse<ScanlationGroupDTO>>
}