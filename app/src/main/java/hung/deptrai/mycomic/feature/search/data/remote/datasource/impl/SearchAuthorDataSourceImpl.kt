package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.core.network.author.SearchAuthorAPI
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchAuthorDataSource
import javax.inject.Inject

class SearchAuthorDataSourceImpl @Inject constructor(
    private val api: SearchAuthorAPI
) : SearchAuthorDataSource{
    override suspend fun getAuthorByName(title: String): ResultWrapper<JsonResponse<DTOject<AuthorAttributes>>> {
        return safeApiCall {
            api.getAuthorByTitle(title)
        }
    }
}