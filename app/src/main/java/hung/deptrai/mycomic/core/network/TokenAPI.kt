package hung.deptrai.mycomic.core.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class RefreshTokenRequest(val refreshToken: String)
data class RefreshTokenResponse(val accessToken: String, val refreshToken: String?)

interface TokenApi {
    @POST("auth/refresh")
    suspend fun refreshAccessToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Response<RefreshTokenResponse>
}