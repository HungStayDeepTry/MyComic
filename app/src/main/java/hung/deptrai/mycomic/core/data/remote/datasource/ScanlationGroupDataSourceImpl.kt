package hung.deptrai.mycomic.core.data.remote.datasource

import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.scanlationGroup.ScanlationGroupAPI
import javax.inject.Inject

class ScanlationGroupDataSourceImpl @Inject constructor(
    private val api: ScanlationGroupAPI
) : ScanlationGroupDataSource{
    override suspend fun getScanlationGroup(ids: List<String>): Result<JsonResponse<DTOject<ScanlationGroupAttributes>>, DataError.Network> {
        return safeApiCall {
            api.getScanlationGroupByIds(ids)
        }
    }
}