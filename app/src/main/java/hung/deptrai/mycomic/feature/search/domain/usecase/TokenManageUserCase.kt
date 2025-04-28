package hung.deptrai.mycomic.feature.search.domain.usecase

import hung.deptrai.mycomic.feature.search.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenManageUserCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend fun saveToken(token: String){
        tokenRepository.saveToken(token)
    }

    fun readToken(): Flow<String>{
        return tokenRepository.readToken()
    }
}