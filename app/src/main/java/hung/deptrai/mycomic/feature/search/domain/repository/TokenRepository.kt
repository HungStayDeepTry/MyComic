package hung.deptrai.mycomic.feature.search.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun saveToken(token: String)
    fun readToken(): Flow<String>
}