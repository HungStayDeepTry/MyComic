package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.mapper.UserDTOtoUserEntity
import hung.deptrai.mycomic.core.domain.model.UserEntity
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchUserDataSource
import hung.deptrai.mycomic.feature.search.domain.repository.SearchUserRepository
import javax.inject.Inject

class SearchUserRepositoryImpl @Inject constructor(
    private val searchUserDataSource: SearchUserDataSource
) : SearchUserRepository{
    override suspend fun searchUserById(token:String, ids: List<String>): ResultWrapper<List<UserEntity>> {
        return when(val response = searchUserDataSource.getUserSearchById(token, ids)){
            is ResultWrapper.Success -> {
                val users = response.data.data
                val rs = users.map {
                    UserDTOtoUserEntity(it)
                }
                ResultWrapper.Success(rs)
            }
            is ResultWrapper.GenericError -> response
            is ResultWrapper.NetworkError -> response
        }
    }
}