package hung.deptrai.mycomic.core.network.author

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthorAPI {
    @GET("author")
    suspend fun getAuthorById(
        @Query("ids[]") ids: List<String>,
        @Query("limit") limit: Int = 100
    ): Response<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes>>>

    @GET("author")
    suspend fun getAuthorByTitle(
        @Query("name") name: String,
        @Query("limit") limit: Int = 20
    ): Response<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes>>>
}