package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.feature.search.data.dto.MangaDTO

interface SearchComicRepository {
    suspend fun searchComicByTitle(title: String): ResultWrapper<List<MangaEntity>>
}