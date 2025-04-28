package hung.deptrai.mycomic.core.network.user

import hung.deptrai.mycomic.core.data.dto.JsonFewestResponse
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.statistic.StatisticsResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.user.UserDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserSearchAPI {
    @GET("user")
    suspend fun getUsers(
        @Header("Authorization") bearerToken: String,
        @Query("ids[]") ids: List<String>
    ): Response<JsonResponse<UserDTO>>
}