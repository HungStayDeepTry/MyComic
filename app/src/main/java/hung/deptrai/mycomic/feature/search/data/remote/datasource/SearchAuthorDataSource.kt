package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes

interface SearchAuthorDataSource {
    suspend fun getAuthorByName(title: String) : Result<JsonResponse<DTOject<AuthorAttributes>>, DataError.Network>
}