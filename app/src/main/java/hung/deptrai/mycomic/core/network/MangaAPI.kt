package hung.deptrai.mycomic.core.network

import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.data.dto.Attributes
import hung.deptrai.mycomic.core.data.dto.tag.TagAttributesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaAPI {
    @GET("manga")
    suspend fun getComicByTitle(
        @Query("title") title: String,
        @Query("limit") limit: Int = 20
    ): Response<JsonResponse<DTOject<Attributes>>>
}