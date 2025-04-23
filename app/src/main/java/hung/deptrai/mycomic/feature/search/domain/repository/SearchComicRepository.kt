package hung.deptrai.mycomic.feature.search.domain.repository

import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO

interface SearchComicRepository {
    suspend fun searchComicByTitle(title: String): MangaDTO?
}