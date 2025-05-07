package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import hung.deptrai.mycomic.core.auth.TokenManager
import hung.deptrai.mycomic.core.domain.Token
import hung.deptrai.mycomic.feature.mock_login.TokenResponse
import hung.deptrai.mycomic.feature.search.data.remote.datasource.TokenDatasource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenDatasourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): TokenDatasource{
    override suspend fun saveToken(token: TokenResponse) {
        TokenManager.saveToken(context, token)
    }

    override fun readToken(): Flow<Token> {
        return TokenManager.readToken(context)
    }
}