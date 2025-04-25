package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.remote.dto.scanlationGroup.ScanlationGroupDTO

interface SearchScanlationGroupDataSource {
    suspend fun getScanlationGroupByTitle(title: String): ResultWrapper<JsonResponse<ScanlationGroupDTO>>
}