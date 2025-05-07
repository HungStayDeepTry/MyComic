package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.domain.Token
import hung.deptrai.mycomic.feature.mock_login.TokenResponse
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun saveToken(token: TokenResponse)
    fun readToken(): Flow<Token>
}