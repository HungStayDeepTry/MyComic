package hung.deptrai.mycomic.feature.explore_manga.data.remote.repository

import hung.deptrai.constants.MdConstants
import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.utils.MdUtil
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.mapper.mangaDTOtoMangaSearch
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource.MangaPageDataSource
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MangaPageRepositoryImpl @Inject constructor(
    private val dataSource: MangaPageDataSource
) : MangaPageRepository{
    override suspend fun fetchMangaPageInfo(): List<Result<List<Any>, DataError.Network>> {

    }

    private fun recentlyAdded(page: Int): Result<List<Any>, DataError.Network>{
        return withContext(Dispatchers.IO){
            val queryParameters = mutableMapOf<String, Any>()
            queryParameters[MdConstants.SearchParameters.limit] = MdConstants.Limits.manga
            queryParameters[MdConstants.SearchParameters.offset] = MdUtil.getMangaListOffset(page)
            when ( val rs = dataSource.recentlyAdded(ProxyRetrofitQueryMap(queryParameters))){
                is Result.Success -> {
                    val data = rs.data.data.map {
                        mangaDTOtoMangaSearch(it)
                    }
                }
            }
        }
    }
}