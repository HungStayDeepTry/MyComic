package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.core.domain.model.TagEntity

interface SearchTagRepository {
    suspend fun getAllTags(): ResultWrapper<List<TagEntity>>
}