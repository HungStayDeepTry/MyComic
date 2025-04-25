package hung.deptrai.mycomic.feature.search.data.remote.datasource.impl

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.safeApiCall
import hung.deptrai.mycomic.core.data.dto.JsonResponse
import hung.deptrai.mycomic.core.network.author.SearchAuthorByTitleAPI
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchAuthorDataSource
import hung.deptrai.mycomic.feature.search.data.remote.dto.author.AuthorDTO
import javax.inject.Inject

class SearchAuthorDataSourceImpl @Inject constructor(
    private val api: SearchAuthorByTitleAPI
) : SearchAuthorDataSource{
    override suspend fun getAuthorByName(title: String): ResultWrapper<JsonResponse<AuthorDTO>> {
        return safeApiCall {
            api.getAuthorByTitle(title)
        }
    }
}