package hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource

import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.Attributes
import hung.deptrai.mycomic.core.data.dto.ListAttributesDto
import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonFewerResponse
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.manga.MangaAPI
import javax.inject.Inject

class MangaPageDataSourceImpl @Inject constructor(
    private val api: MangaAPI
) : MangaPageDataSource{
    override suspend fun recentlyAdded(queryMap: ProxyRetrofitQueryMap): Result<JsonResponse<DTOject<Attributes>>, DataError.Network> {
        return safeApiCall {
            api.recentlyAdded(queryMap)
        }
    }

    override suspend fun popularNewTitles(queryMap: ProxyRetrofitQueryMap): Result<JsonResponse<DTOject<Attributes>>, DataError.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchList(listId: String): Result<JsonFewerResponse<DTOject<ListAttributesDto>>, DataError.Network> {
        TODO("Not yet implemented")
    }

}