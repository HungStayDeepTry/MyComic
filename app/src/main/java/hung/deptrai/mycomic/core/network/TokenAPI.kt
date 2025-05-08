package hung.deptrai.mycomic.core.network

import hung.deptrai.mycomic.core.domain.Token
import hung.deptrai.mycomic.feature.mock_login.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface TokenApi {
    @POST("auth/refresh")
    suspend fun refreshAccessToken(
        @Body refreshTokenRequest: Token
    ): Response<TokenResponse>
}