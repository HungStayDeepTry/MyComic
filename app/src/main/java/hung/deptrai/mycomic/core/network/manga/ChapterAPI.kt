package hung.deptrai.mycomic.core.network.manga

import hung.deptrai.constants.MdConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface ChapterAPI {
    @GET("${MdConstants.Api.chapter}?order[updatedAt]=desc")
    suspend fun getLatestChapters(
        @Query("limit") limit: Int
    )
}