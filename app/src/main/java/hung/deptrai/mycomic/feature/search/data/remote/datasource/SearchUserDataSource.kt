package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.data.dto.user.UserAttributesDTO
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface SearchUserDataSource {
    suspend fun getUserSearchById(token: String, ids: List<String>): Result<JsonResponse<DTOject<UserAttributesDTO>>, DataError.Network>
}