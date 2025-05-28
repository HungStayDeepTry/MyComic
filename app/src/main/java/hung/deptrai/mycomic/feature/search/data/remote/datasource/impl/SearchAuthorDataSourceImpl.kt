package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.author.AuthorAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchAuthorDataSource
import javax.inject.Inject

class SearchAuthorDataSourceImpl @Inject constructor(
    private val api: AuthorAPI
) : SearchAuthorDataSource{
    override suspend fun getAuthorByName(title: String): Result<hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes>>, DataError.Network> {
        return safeApiCall {
            api.getAuthorByTitle(title)
        }
    }
}