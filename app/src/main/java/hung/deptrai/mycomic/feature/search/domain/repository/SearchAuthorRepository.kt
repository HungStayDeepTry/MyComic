package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.model.AuthorEntity
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorDTO

interface SearchAuthorRepository {
    suspend fun searchAuthorByTitle(title: String): ResultWrapper<List<AuthorEntity>>
}