package hung.deptrai.mycomic.core.data.remote.datasource

import hung.deptrai.mycomic.core.data.remote.dto.ChapterDTO
import hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface ScanlationGroupDataSource {
    suspend fun getScanlationGroup(ids: List<String>): Result<JsonResponse<DTOject<ScanlationGroupAttributes>>, DataError.Network>
}