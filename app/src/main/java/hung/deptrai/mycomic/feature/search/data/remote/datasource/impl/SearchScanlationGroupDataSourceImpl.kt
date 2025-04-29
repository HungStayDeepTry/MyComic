package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.core.network.scanlationGroup.SearchScanlationGroupAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchScanlationGroupDataSource
import hung.deptrai.mycomic.feature.search.data.dto.scanlationGroup.ScanlationGroupDTO
import javax.inject.Inject

class SearchScanlationGroupDataSourceImpl @Inject constructor(
    private val api : SearchScanlationGroupAPI
) : SearchScanlationGroupDataSource{
    override suspend fun getScanlationGroupByTitle(title: String): ResultWrapper<JsonResponse<hung.deptrai.mycomic.feature.search.data.dto.scanlationGroup.ScanlationGroupDTO>> {
        return safeApiCall {
            api.getScanlationGroupByTitle(title)
        }
    }
}