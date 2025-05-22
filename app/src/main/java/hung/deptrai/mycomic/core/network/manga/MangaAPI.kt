package hung.deptrai.mycomic.core.network.manga

import hung.deptrai.constants.MdConstants
import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.remote.dto.Attributes
import hung.deptrai.mycomic.core.data.remote.dto.ListAttributesDto
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonFewestResponse
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MangaAPI {
    @GET("manga")
    suspend fun getComicByTitle(
        @Query("title") title: String,
        @Query("limit") limit: Int = 20
    ): Response<JsonResponse<DTOject<Attributes>>>

    @GET("manga")
    suspend fun getComicByIds(
        @Query("ids[]") mangaIds: List<String>,
        @Query("includes[]") includes: List<String> = listOf("cover_art")
    ): Response<JsonResponse<DTOject1<Attributes>>>

    @GET(
        "${MdConstants.Api.manga}?&order[createdAt]=desc&includes[]=${MdConstants.Types.coverArt}"
    )
    suspend fun recentlyAdded(
        @QueryMap options: ProxyRetrofitQueryMap
    ): Response<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1<hung.deptrai.mycomic.core.data.remote.dto.Attributes>>>

    @GET(
        "${MdConstants.Api.manga}?&order[followedCount]=desc&includes[]=${MdConstants.Types.coverArt}&hasAvailableChapters=true"
    )
    suspend fun popularNewReleases(
        @QueryMap options: ProxyRetrofitQueryMap
    ): Response<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1<hung.deptrai.mycomic.core.data.remote.dto.Attributes>>>

    @GET(
        "${MdConstants.Api.list}/{id}"
    )
    suspend fun viewList(
        @Path("id") id: String
    ): Response<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonFewestResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.ListAttributesDto>>>
}