package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.data.dto.scanlationGroup.ScanlationGroupAttributes
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface SearchScanlationGroupDataSource {
    suspend fun getScanlationGroupByTitle(title: String): Result<JsonResponse<DTOject<ScanlationGroupAttributes>>, DataError.Network>
}