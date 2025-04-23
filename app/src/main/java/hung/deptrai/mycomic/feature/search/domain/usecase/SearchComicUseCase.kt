package hung.deptrai.mycomic.feature.search.domain.usecase

import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.domain.repository.SearchComicRepository
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import javax.inject.Inject

class SearchComicUseCase @Inject constructor(
    private val searchComicRepository: SearchComicRepository
) {
    private fun mapToSearchComicModel(mangaEntities: List<MangaEntity>): List<SearchComic> {
        return mangaEntities.map { entity ->
            val title = entity.title.en ?: "No title"
            val description = entity.description?.en ?: "No description"

            val imageUrl = entity.coverArt?.attributes?.fileName?.let { fileName ->
                // Dựa theo format ảnh của MangaDex
                "https://uploads.mangadex.org/covers/${entity.id}/$fileName.256.jpg"
            } ?: ""

            SearchComic(
                id = entity.id,
                title = title,
                description = description,
                imageUrl = imageUrl,
                status = entity.status ?: ""
            )
        }
    }

    suspend fun searchComicByTitle(title: String): List<SearchComic> {
        // Lấy dữ liệu từ repository
        val mangaDTO = searchComicRepository.searchComicByTitle(title)
        // Chuyển đổi từ MangaDTO sang SearchComicModel
        return mapToSearchComicModel(mangaDTO) ?: emptyList()
    }
}