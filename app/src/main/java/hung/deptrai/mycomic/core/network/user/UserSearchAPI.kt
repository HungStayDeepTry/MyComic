package hung.deptrai.mycomic.core.network.user

import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.data.dto.user.UserAttributesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserSearchAPI {
    @GET("user")
    suspend fun getUsers(
        @Header("Authorization") bearerToken: String,
        @Query("ids[]") ids: List<String>
    ): Response<JsonResponse<DTOject<UserAttributesDTO>>>
}