package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.feature.search.domain.model.TagSearch
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface SearchTagRepository {
    suspend fun getTags(ids: List<String>): Result<List<TagSearch>, DataError.Network>
}