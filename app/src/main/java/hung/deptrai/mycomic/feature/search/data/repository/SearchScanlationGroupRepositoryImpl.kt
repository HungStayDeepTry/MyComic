package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.mapper.ScanlationGrouptoScanlationEntity
import hung.deptrai.mycomic.core.domain.model.ScanlationGroupEntity
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchScanlationGroupDataSource
import hung.deptrai.mycomic.feature.search.data.remote.dto.scanlationGroup.ScanlationGroupDTO
import hung.deptrai.mycomic.feature.search.domain.repository.SearchScanlationGroupRepository
import javax.inject.Inject

class SearchScanlationGroupRepositoryImpl @Inject constructor(
    private val searchScanlationGroupDataSource: SearchScanlationGroupDataSource
) : SearchScanlationGroupRepository{
    override suspend fun searchScanlationGroupByTitle(title: String): ResultWrapper<List<ScanlationGroupEntity>> {
        val response = searchScanlationGroupDataSource.getScanlationGroupByTitle(title)
        return when(response){
            is ResultWrapper.Success ->{
                val scanlationGroups = (response.data?.data)
                val rs = scanlationGroups?.map {
                    ScanlationGrouptoScanlationEntity(it)
                } ?: emptyList()
                ResultWrapper.Success(rs)
            }
            is ResultWrapper.GenericError -> response
            is ResultWrapper.NetworkError -> response
        }
    }
}
