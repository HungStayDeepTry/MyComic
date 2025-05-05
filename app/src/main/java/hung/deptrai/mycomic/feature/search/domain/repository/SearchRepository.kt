package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.feature.search.data.dto.Attributes
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.dto.scanlationGroup.ScanlationGroupAttributes
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagAttributesDTO
import hung.deptrai.mycomic.feature.search.data.dto.user.UserAttributesDTO

interface SearchRepository {
    suspend fun searchAuthorByTitle(title: String): ResultWrapper<List<DTOject<AuthorAttributes>>>
    suspend fun searchComicByTitle(title: String): ResultWrapper<List<DTOject<Attributes>>>
    suspend fun searchScanlationGroupByTitle(title: String): ResultWrapper<List<DTOject<ScanlationGroupAttributes>>>
    suspend fun getAllTags(): ResultWrapper<List<DTOject<TagAttributesDTO>>>
    suspend fun searchUserById(token: String, ids: List<String>): ResultWrapper<List<DTOject<UserAttributesDTO>>>
}