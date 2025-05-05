package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.model.AuthorEntity

interface SearchAuthorRepository {
    suspend fun searchAuthorByTitle(title: String): ResultWrapper<List<AuthorEntity>>
}