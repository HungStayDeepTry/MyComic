package hung.deptrai.mycomic.core.network.tag

import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagDTO
import retrofit2.Response
import retrofit2.http.GET

interface GetAllTagsAPI {
    @GET("/manga/tag")
    fun getTags(): Response<JsonResponse<TagDTO>>
}