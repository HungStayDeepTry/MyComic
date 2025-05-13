package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.data.dto.author.AuthorAttributes

interface SearchAuthorDataSource {
    suspend fun getAuthorByName(title: String) : Result<JsonResponse<DTOject<AuthorAttributes>>, DataError.Network>
}