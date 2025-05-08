package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.feature.search.domain.SearchType
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface SearchRepository {
    suspend fun searchByTitle(title: String, type: SearchType, isLoggedIn: Boolean): List<Result<List<Any>, DataError.Network>>
}