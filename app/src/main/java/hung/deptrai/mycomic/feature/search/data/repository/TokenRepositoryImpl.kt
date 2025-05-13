package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.core.domain.Token
import hung.deptrai.mycomic.feature.mock_login.TokenResponse
import hung.deptrai.mycomic.feature.search.data.remote.datasource.TokenDatasource
import hung.deptrai.mycomic.feature.search.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDatasource: TokenDatasource
) : TokenRepository{
    override suspend fun saveToken(token: TokenResponse) {
        tokenDatasource.saveToken(token)
    }

    override fun readToken(): Flow<Token> {
        return tokenDatasource.readToken()
    }
}