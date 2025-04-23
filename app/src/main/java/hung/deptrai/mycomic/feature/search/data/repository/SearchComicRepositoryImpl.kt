package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.core.domain.mapper.mapMangaDtoToEntity
import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.core.token.TokenManager
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchDataSource
import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.domain.repository.SearchComicRepository
import javax.inject.Inject

class SearchComicRepositoryImpl @Inject constructor(
    private val searchMangaDataSource: SearchDataSource
) : SearchComicRepository{
    override suspend fun searchComicByTitle(title: String): List<MangaEntity> {
        val dto = searchMangaDataSource.getComicByTitle(title)
        return dto?.let { mapMangaDtoToEntity(it) } ?: emptyList()
    }
}