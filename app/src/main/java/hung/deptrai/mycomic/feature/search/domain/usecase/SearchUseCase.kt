package hung.deptrai.mycomic.feature.search.domain.usecase

import hung.deptrai.mycomic.core.domain.exception.Error
import hung.deptrai.mycomic.core.domain.exception.QueryError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.search.domain.SearchType
import hung.deptrai.mycomic.feature.search.domain.repository.SearchRepository
import hung.deptrai.mycomic.feature.search.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun search(title: String, type: SearchType): List<Result<List<Any>, Error>>{
        val isLoggedIn = tokenRepository.readToken().first().accessToken.isNotEmpty()
        if(type == SearchType.SCANLATION_GROUP  && !isLoggedIn){
            return listOf(Result.Error(QueryError.USER_NOT_LOGGED_IN))
        }
        return searchRepository.searchByTitle(title, type, isLoggedIn)
    }
}