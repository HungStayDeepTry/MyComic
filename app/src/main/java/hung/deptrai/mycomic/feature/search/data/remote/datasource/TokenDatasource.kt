package hung.deptrai.mycomic.feature.search.data.remote.datasource

import kotlinx.coroutines.flow.Flow

interface TokenDatasource {
    suspend fun saveToken(token: String)
    fun readToken(): Flow<String>
}