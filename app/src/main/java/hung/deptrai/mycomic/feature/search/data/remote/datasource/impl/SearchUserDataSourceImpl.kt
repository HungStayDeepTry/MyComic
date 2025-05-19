package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.user.UserSearchAPI
import hung.deptrai.mycomic.core.data.remote.dto.user.UserAttributesDTO
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchUserDataSource
import javax.inject.Inject

class SearchUserDataSourceImpl @Inject constructor(
    private val api: UserSearchAPI
): SearchUserDataSource{
    override suspend fun getUserSearchById(token: String, ids: List<String>): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.user.UserAttributesDTO>>, DataError.Network> {
        return safeApiCall {
            api.getUsers("Bearer "+token, ids)
        }
    }
}