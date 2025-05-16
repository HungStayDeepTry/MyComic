package hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource

import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.dto.Attributes
import hung.deptrai.mycomic.core.data.dto.ListAttributesDto
import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonFewerResponse
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface MangaPageDataSource {
    suspend fun recentlyAdded(queryMap: ProxyRetrofitQueryMap): Result<JsonResponse<DTOject<Attributes>>, DataError.Network>
    suspend fun popularNewTitles(queryMap: ProxyRetrofitQueryMap): Result<JsonResponse<DTOject<Attributes>>, DataError.Network>
    suspend fun fetchList(listId: String): Result<JsonFewerResponse<DTOject<ListAttributesDto>>, DataError.Network>
}