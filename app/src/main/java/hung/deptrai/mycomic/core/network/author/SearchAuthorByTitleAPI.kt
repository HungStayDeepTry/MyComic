package hung.deptrai.mycomic.core.network.author

import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.author.AuthorDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAuthorByTitleAPI {
    @GET("author")
    suspend fun getAuthorByTitle(
        @Query("name") name: String,
        @Query("limit") limit: Int = 15
    ): Response<JsonResponse<AuthorDTO>>
}