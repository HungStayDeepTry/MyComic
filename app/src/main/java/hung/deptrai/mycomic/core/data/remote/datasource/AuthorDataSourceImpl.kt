package hung.deptrai.mycomic.core.data.remote.datasource

import hung.deptrai.mycomic.core.data.common.safeApiCall
import hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.JsonResponse
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.core.network.author.AuthorAPI
import javax.inject.Inject

class AuthorDataSourceImpl @Inject constructor(
    private val api: AuthorAPI
) : AuthorDataSource{
    override suspend fun getAuthorById(authorIds: List<String>): Result<JsonResponse<DTOject<AuthorAttributes>>, DataError.Network> {
        return safeApiCall {
            api.getAuthorById(authorIds)
        }
    }
}