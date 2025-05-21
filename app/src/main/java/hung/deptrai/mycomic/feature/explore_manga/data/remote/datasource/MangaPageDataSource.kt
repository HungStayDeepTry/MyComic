package hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource

import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.remote.dto.Attributes
import hung.deptrai.mycomic.core.data.remote.dto.ListAttributesDto
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonFewerResponse
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface MangaPageDataSource {
    suspend fun recentlyAdded(queryMap: ProxyRetrofitQueryMap): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1<hung.deptrai.mycomic.core.data.remote.dto.Attributes>>, DataError.Network>
    suspend fun popularNewTitles(queryMap: ProxyRetrofitQueryMap): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1<hung.deptrai.mycomic.core.data.remote.dto.Attributes>>, DataError.Network>
    suspend fun fetchList(listId: String): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonFewestResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.ListAttributesDto>>, DataError.Network>
    suspend fun getMangaByIds(mangaIds: List<String>): Result<JsonResponse<DTOject1<Attributes>>, DataError.Network>
}