package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.JsonFewestResponse
import hung.deptrai.mycomic.core.network.user.UserSearchAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchUserDataSource
import hung.deptrai.mycomic.feature.search.data.remote.dto.user.UserDTO
import javax.inject.Inject

class SearchUserDataSourceImpl @Inject constructor(
    private val api: UserSearchAPI
): SearchUserDataSource{
    override suspend fun getUserSearchById(id: String): ResultWrapper<JsonFewestResponse<UserDTO>> {
        return safeApiCall {
            api.getStatisticsForManga(id)
        }
    }
}