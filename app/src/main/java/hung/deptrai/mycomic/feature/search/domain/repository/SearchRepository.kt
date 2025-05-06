package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.feature.search.domain.SearchType
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface SearchRepository {
//    suspend fun searchAuthorByTitle(title: String): ResultWrapper<List<DTOject<AuthorAttributes>>>
    suspend fun searchByTitle(title: String, type: SearchType): Result<List<Any>, DataError.Network>
//    suspend fun searchScanlationGroupByTitle(title: String): ResultWrapper<List<DTOject<ScanlationGroupAttributes>>>
//    suspend fun getAllTags(): ResultWrapper<List<DTOject<TagAttributesDTO>>>
//    suspend fun searchUserById(token: String, ids: List<String>): ResultWrapper<List<DTOject<UserAttributesDTO>>>
}