package hung.deptrai.mycomic.feature.search.data.remote.datasource

import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO

interface SearchDataSource {
    suspend fun getComicByTitle(title: String): MangaDTO?
}