package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import hung.deptrai.mycomic.core.data.local.TokenManager
import hung.deptrai.mycomic.feature.search.data.remote.datasource.TokenDatasource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenDatasourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): TokenDatasource{
    override suspend fun saveToken(token: String) {
        TokenManager.saveToken(context, token)
    }

    override fun readToken(): Flow<String> {
        return TokenManager.readToken(context)
    }
}