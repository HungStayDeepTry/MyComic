package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.author.SearchAuthorAPI
import hung.deptrai.mycomic.core.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchAuthorDataSource
import javax.inject.Inject

class SearchAuthorDataSourceImpl @Inject constructor(
    private val api: SearchAuthorAPI
) : SearchAuthorDataSource{
    override suspend fun getAuthorByName(title: String): Result<JsonResponse<DTOject<AuthorAttributes>>, DataError.Network> {
        return safeApiCall {
            api.getAuthorByTitle(title)
        }
    }
}