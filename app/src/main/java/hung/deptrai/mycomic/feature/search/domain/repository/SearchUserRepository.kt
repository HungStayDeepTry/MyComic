package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.model.ScanlationGroupEntity

interface SearchUserRepository {
    suspend fun searchUserById(id: String): ResultWrapper<List<ScanlationGroupEntity>>
}