package hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource

import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.data.remote.dto.Attributes
import hung.deptrai.mycomic.core.data.remote.dto.ListAttributesDto
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonFewerResponse
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.manga.MangaAPI
import javax.inject.Inject

class MangaPageDataSourceImpl @Inject constructor(
    private val api: MangaAPI
) : MangaPageDataSource{
    override suspend fun recentlyAdded(queryMap: ProxyRetrofitQueryMap): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1<hung.deptrai.mycomic.core.data.remote.dto.Attributes>>, DataError.Network> {
        return safeApiCall {
            api.recentlyAdded(queryMap)
        }
    }

    override suspend fun popularNewTitles(queryMap: ProxyRetrofitQueryMap): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1<hung.deptrai.mycomic.core.data.remote.dto.Attributes>>, DataError.Network> {
        return safeApiCall {
            api.popularNewReleases(queryMap)
        }
    }

    override suspend fun fetchCustomList(listId: String): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonFewestResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.ListAttributesDto>>, DataError.Network> {
        return safeApiCall {
            api.viewList(listId)
        }
    }

    override suspend fun getMangaByIds(mangaIds: List<String>): Result<JsonResponse<DTOject1<Attributes>>, DataError.Network> {
        return safeApiCall {
            api.getComicByIds(mangaIds)
        }
    }
}