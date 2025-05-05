package hung.deptrai.mycomic.core.network.author

import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAuthorAPI {
    @GET("author")
    suspend fun getAuthorById(
        @Query("ids[]") ids: List<String>,
        @Query("limit") limit: Int = 100
    ): Response<JsonResponse<DTOject<AuthorAttributes>>>

    @GET("author")
    suspend fun getAuthorByTitle(
        @Query("name") name: String,
        @Query("limit") limit: Int = 20
    ): Response<JsonResponse<DTOject<AuthorAttributes>>>
}