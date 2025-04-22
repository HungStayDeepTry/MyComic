package hung.deptrai.mycomic.feature.search.data.remote

import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchComicAPI {
    @GET("manga")
    suspend fun getComicByTitle(
        @Query("title") title: String
    ): Response<MangaDTO>
}