package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.model.ScanlationGroupEntity

interface SearchScanlationGroupRepository {
    suspend fun searchScanlationGroupByTitle(title: String): ResultWrapper<List<ScanlationGroupEntity>>
}