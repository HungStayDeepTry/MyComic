package hung.deptrai.mycomic.core.auth

import android.content.Context
import hung.deptrai.mycomic.core.domain.Token
import hung.deptrai.mycomic.feature.mock_login.TokenResponse
import kotlinx.coroutines.flow.Flow

object TokenManager {

    suspend fun saveToken(context: Context, tokenResponse: TokenResponse) {
        val expiresAt = System.currentTimeMillis() + tokenResponse.expires_in * 1000
        val tokenInfo = Token(
            accessToken = tokenResponse.access_token,
            refreshToken = tokenResponse.refresh_token,
            expiresAt = expiresAt
        )
        context.tokenDataStore.updateData { tokenInfo }

        // 🔁 Lên lịch kiểm tra token sắp hết hạn
        TokenRefreshWorker.schedule(context, tokenResponse.expires_in * 1000L - 60_000L)
    }

    fun readToken(context: Context): Flow<Token> {
        return context.tokenDataStore.data
    }

    suspend fun clearToken(context: Context) {
        context.tokenDataStore.updateData { Token() }
    }
}