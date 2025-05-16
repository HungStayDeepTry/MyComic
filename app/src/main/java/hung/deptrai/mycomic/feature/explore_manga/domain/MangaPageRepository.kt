package hung.deptrai.mycomic.feature.explore_manga.domain

import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result

interface MangaPageRepository {
    suspend fun fetchMangaPageInfo(): List<Result<List<Any>, DataError.Network>>
}