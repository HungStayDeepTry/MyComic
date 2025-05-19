package hung.deptrai.mycomic.core.network.scanlationGroup

import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchScanlationGroupAPI {
    @GET("group")
    suspend fun getScanlationGroupByTitle(
        @Query("name") name: String,
        @Query("limit") limit: Int = 20
    ): Response<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes>>>
}