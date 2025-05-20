package hung.deptrai.mycomic.feature.explore_manga.domain

import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import kotlinx.coroutines.flow.Flow

interface MangaPageRepository {
    suspend fun fetchMangaPageInfo(): List<Result<List<Any>, DataError.Network>>
    fun observeMangaByType(type: CustomType): Flow<List<HomeMangaEntity>>
}