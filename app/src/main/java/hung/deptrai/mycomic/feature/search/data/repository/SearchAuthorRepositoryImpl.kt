package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.mapper.AuthorDTOtoAuthorEntity
import hung.deptrai.mycomic.core.domain.mapper.ScanlationGrouptoScanlationEntity
import hung.deptrai.mycomic.core.domain.model.AuthorEntity
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchAuthorDataSource
import hung.deptrai.mycomic.feature.search.domain.repository.SearchAuthorRepository
import javax.inject.Inject

class SearchAuthorRepositoryImpl @Inject constructor(
    private val searchAuthorDataSource: SearchAuthorDataSource
) : SearchAuthorRepository{
    override suspend fun searchAuthorByTitle(title: String): ResultWrapper<List<AuthorEntity>> {
        return when(val response = searchAuthorDataSource.getAuthorByName(title)){
            is ResultWrapper.Success ->{
                val scanlationGroups = (response.data.data)
                val rs = scanlationGroups.map {
                    AuthorDTOtoAuthorEntity(it)
                }
                ResultWrapper.Success(rs)
            }
            is ResultWrapper.GenericError -> response
            is ResultWrapper.NetworkError -> response
        }
    }
}