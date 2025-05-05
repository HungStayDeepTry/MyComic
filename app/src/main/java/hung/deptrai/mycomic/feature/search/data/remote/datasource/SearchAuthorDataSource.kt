package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes

interface SearchAuthorDataSource {
    suspend fun getAuthorByName(title: String) : ResultWrapper<JsonResponse<DTOject<AuthorAttributes>>>
}