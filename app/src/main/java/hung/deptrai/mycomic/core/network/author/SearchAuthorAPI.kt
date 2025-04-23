package hung.deptrai.mycomic.core.network.author

import hung.deptrai.mycomic.feature.search.data.remote.dto.author.AuthorDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchAuthorAPI {
    @GET("author/{id}")
    suspend fun getAuthorById(@Path("id") id: String): Response<AuthorDTO>
}