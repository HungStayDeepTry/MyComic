package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.model.ScanlationGroupEntity
import hung.deptrai.mycomic.core.domain.model.UserEntity

interface SearchUserRepository {
    suspend fun searchUserById(token: String, ids: List<String>): ResultWrapper<List<UserEntity>>
}