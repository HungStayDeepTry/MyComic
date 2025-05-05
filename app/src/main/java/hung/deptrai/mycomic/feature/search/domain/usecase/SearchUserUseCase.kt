package hung.deptrai.mycomic.feature.search.domain.usecase

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.model.UserEntity
import hung.deptrai.mycomic.feature.search.domain.repository.SearchUserRepository
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.UserSearch
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val searchUserRepository: SearchUserRepository
) {
    suspend fun getUsersByGroup(token: String, userIds: List<String>): ResultWrapper<List<UserSearch>>{
        return when(val resultWrapper = searchUserRepository.searchUserById(token, userIds)){
            is ResultWrapper.Success -> {
                val rs = mapToUserSearchByScanGroup(resultWrapper.data)
                ResultWrapper.Success(rs)
            }
            is ResultWrapper.GenericError -> {
                // Trả về lỗi chi tiết cho UI
                ResultWrapper.GenericError(
                    code = resultWrapper.code,
                    error = "Error: ${resultWrapper.error}"
                )
            }
            is ResultWrapper.NetworkError -> {
                // Lỗi mạng, có thể cung cấp thêm thông báo lỗi chi tiết
                ResultWrapper.NetworkError(
                    exception = resultWrapper.exception
                )
            }
        }
    }
    private fun mapToUserSearchByScanGroup(userEntitys: List<UserEntity>): List<UserSearch>{
        val userSearchByGroups = userEntitys.map { en ->
            UserSearch(
                id = en.id,
                name = en.username
            )
        }
        return userSearchByGroups
    }
}