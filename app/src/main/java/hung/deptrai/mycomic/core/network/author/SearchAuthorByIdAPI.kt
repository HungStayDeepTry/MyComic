package hung.deptrai.mycomic.core.network.author

import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAuthorByIdAPI {
    @GET("author")
    suspend fun getAuthorById(
        @Query("ids[]") ids: List<String>,
        @Query("limit") limit: Int = 100
    ): Response<hung.deptrai.mycomic.feature.search.data.dto.author.AuthorResponse>
}