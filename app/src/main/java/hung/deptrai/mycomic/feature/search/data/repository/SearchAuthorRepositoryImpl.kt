package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchAuthorDataSource
import hung.deptrai.mycomic.feature.search.domain.repository.SearchAuthorRepository
import javax.inject.Inject

class SearchAuthorRepositoryImpl @Inject constructor(
    private val searchAuthorDataSource: SearchAuthorDataSource
) : SearchAuthorRepository{
    override suspend fun searchAuthorByTitle(title: String): ResultWrapper<List<DTOject<AuthorAttributes>>> {
        return when(val response = searchAuthorDataSource.getAuthorByName(title)){
            is ResultWrapper.Success ->{
                val scanlationGroups = (response.data.data)
//                val rs = scanlationGroups.map {
//                    AuthorDTOtoAuthorSearch(it)
//                }
                ResultWrapper.Success(scanlationGroups)
            }
            is ResultWrapper.GenericError -> response
            is ResultWrapper.NetworkError -> response
        }
    }
}