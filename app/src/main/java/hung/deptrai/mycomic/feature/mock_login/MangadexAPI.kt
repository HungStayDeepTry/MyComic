package hung.deptrai.mycomic.feature.mock_login

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object MangaDexApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.mangadex.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build())
        .build()

    private val service = retrofit.create(ApiService::class.java)

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(username, password)
        return service.login(request)
    }

    interface ApiService {
        @POST("auth/login")
        suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    }
}
data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val result: String,
    val token: TokenInfo
) {
    val accessToken: String
        get() = token.session
}

data class TokenInfo(
    val session: String,
    val refresh: String
)