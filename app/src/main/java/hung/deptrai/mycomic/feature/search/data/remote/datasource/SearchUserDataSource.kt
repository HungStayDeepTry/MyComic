package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.JsonFewestResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.user.UserDTO

interface SearchUserDataSource {
    suspend fun getUserSearchById(id: String): ResultWrapper<JsonFewestResponse<UserDTO>>
}