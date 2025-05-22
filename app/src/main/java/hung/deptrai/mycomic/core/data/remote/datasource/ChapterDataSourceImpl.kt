package hung.deptrai.mycomic.core.data.remote.datasource

import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.data.remote.dto.ChapterDTO
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.manga.ChapterAPI
import javax.inject.Inject

class ChapterDataSourceImpl @Inject constructor(
    private val api: ChapterAPI
) : ChapterDataSource{
    override suspend fun getLatestUpdatedChapter(): Result<JsonResponse<DTOject<ChapterDTO>>, DataError.Network> {
        return safeApiCall {
            api.getLatestChapters()
        }
    }
}