package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.core.network.tag.GetAllTagsAPI
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagDTO
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchTagDataSource
import javax.inject.Inject

class SearchTagDataSourceImpl @Inject constructor(
    private val api: GetAllTagsAPI
) : SearchTagDataSource{
    override suspend fun fetchAllTags(): ResultWrapper<JsonResponse<TagDTO>> {
        return safeApiCall { api.getTags() }
    }
}