package hung.deptrai.mycomic.feature.search.data.remote

import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.MangaDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchComicAPI {
    @GET("manga")
    suspend fun getComicByTitle(
//        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("limit") limit: Int = 20
    ): Response<JsonResponse<MangaDTO>>



}