package hung.deptrai.mycomic.core.network.manga

import hung.deptrai.constants.MdConstants
import hung.deptrai.mycomic.core.data.remote.dto.ChapterDTO
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChapterAPI {
    @GET("${MdConstants.Api.chapter}?order[readableAt]=desc")
    suspend fun getLatestChapters(
        @Query("limit") limit: Int = 10
    ) : Response<JsonResponse<DTOject<ChapterDTO>>>
}