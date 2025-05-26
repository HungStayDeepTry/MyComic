package hung.deptrai.mycomic.core.data.remote.datasource

import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.data.remote.dto.coverArt.CoverArtAttributes
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
import javax.inject.Inject

class MangaDataSourceImpl @Inject constructor(
    private val api: CoverArtAPI
) : MangaDataSource{
    override suspend fun getCoverArtById(coverArtIds: List<String>): Result<JsonResponse<DTOject<CoverArtAttributes>>, DataError.Network> {
        return safeApiCall {
            api.getCoverArtById(coverArtIds)
        }
    }

}