package hung.deptrai.mycomic.feature.search.data.remote

import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.Attributes
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagAttributesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchComicAPI {
    @GET("manga")
    suspend fun getComicByTitle(
//        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("limit") limit: Int = 20
    ): Response<JsonResponse<DTOject<Attributes>>>

    @GET("/manga/tag")
    suspend fun getTags(): Response<JsonResponse<DTOject<TagAttributesDTO>>>
}