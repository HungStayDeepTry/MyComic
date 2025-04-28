package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.feature.search.data.remote.datasource.TokenDatasource
import hung.deptrai.mycomic.feature.search.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDatasource: TokenDatasource
) : TokenRepository{
    override suspend fun saveToken(token: String) {
        tokenDatasource.saveToken(token)
    }

    override fun readToken(): Flow<String> {
        return tokenDatasource.readToken()
    }
}