package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.core.token.TokenManager
import hung.deptrai.mycomic.feature.search.data.remote.SearchComicAPI
import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val api: SearchComicAPI
) : SearchDataSource{
    override suspend fun getComicByTitle(title: String): MangaDTO? {
        val response = api.getComicByTitle(title)
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

}