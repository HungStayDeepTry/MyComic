package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.scanlationGroup.ScanlationGroupAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchScanlationGroupDataSource
import javax.inject.Inject

class SearchScanlationGroupDataSourceImpl @Inject constructor(
    private val api : ScanlationGroupAPI
) : SearchScanlationGroupDataSource{
    override suspend fun getScanlationGroupByTitle(title: String): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes>>, DataError.Network> {
        return safeApiCall {
            api.getScanlationGroupByTitle(title)
        }
    }
}