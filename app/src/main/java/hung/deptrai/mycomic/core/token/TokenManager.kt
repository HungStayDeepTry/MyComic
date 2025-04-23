package hung.deptrai.mycomic.core.token

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import hung.deptrai.mycomic.core.data.local.encryptedDataStore
import hung.deptrai.mycomic.core.network.RefreshTokenRequest
import hung.deptrai.mycomic.core.network.TokenApi
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val context: Context,
    private val tokenApi: TokenApi
) {
    private val dataStore = context.encryptedDataStore

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String?) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = encrypt(accessToken)
            refreshToken?.let { prefs[REFRESH_TOKEN_KEY] = encrypt(it) }
        }
    }

    suspend fun getAccessToken(): String? {
        return dataStore.data.first()[ACCESS_TOKEN_KEY]?.let { decrypt(it) }
    }

    suspend fun getRefreshToken(): String? {
        return dataStore.data.first()[REFRESH_TOKEN_KEY]?.let { decrypt(it) }
    }

    suspend fun refreshAccessTokenIfNeeded(
        onTokenReady: suspend (accessToken: String) -> Unit
    ) {
        val accessToken = getAccessToken()
        if (accessToken != null && !isExpired(accessToken)) {
            onTokenReady(accessToken)
            return
        }

        val refreshToken = getRefreshToken()
        if (refreshToken.isNullOrEmpty()) throw Exception("Refresh token is missing")

        val response = tokenApi.refreshAccessToken(RefreshTokenRequest(refreshToken))

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                saveTokens(body.accessToken, body.refreshToken)
                onTokenReady(body.accessToken)
            } else throw Exception("Empty token response")
        } else throw Exception("Failed to refresh token: ${response.code()}")
    }

    private fun encrypt(input: String): String {
        // Replace with AES or EncryptedSharedPrefs logic
        return input
    }

    private fun decrypt(input: String): String {
        // Replace with AES or EncryptedSharedPrefs logic
        return input
    }

    private fun isExpired(token: String): Boolean {
        // Optional: Decode JWT and check `exp`, or hardcode temporary logic
        return false // For now always valid, replace with real check
    }
}
