package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonFewestResponse
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.user.UserAttributesDTO
import hung.deptrai.mycomic.feature.search.data.dto.user.UserDTO

interface SearchUserDataSource {
    suspend fun getUserSearchById(token: String, ids: List<String>): ResultWrapper<JsonResponse<DTOject<UserAttributesDTO>>>
}