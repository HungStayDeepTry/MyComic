package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagAttributesDTO
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagDTO

interface SearchTagDataSource {
    suspend fun fetchAllTags(): ResultWrapper<JsonResponse<DTOject<TagAttributesDTO>>>
}