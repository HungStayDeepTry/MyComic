package hung.deptrai.mycomic.feature.search.domain.usecase

import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.domain.repository.SearchComicRepository
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import javax.inject.Inject

class SearchComicUseCase @Inject constructor(
    private val searchComicRepository: SearchComicRepository
) {
    private fun mapToSearchComicModel(mangaDTO: MangaDTO): List<SearchComic> {
        return mangaDTO.data.map { data ->
            val title = data.attributes.title.en ?: "No title"
            val description = data.attributes.description.en ?: "No description"
            val imageUrl = data.attributes.links.raw ?: ""  // Bạn có thể chọn trường ảnh từ response nếu có
            val status = data.attributes.status

            SearchComic(
                id = data.id,
                title = title,
                description = description,
                imageUrl = imageUrl,
                status = status
            )
        }
    }

    suspend fun searchComicByTitle(title: String): List<SearchComic> {
        // Lấy dữ liệu từ repository
        val mangaDTO = searchComicRepository.searchComicByTitle(title)
        // Chuyển đổi từ MangaDTO sang SearchComicModel
        return mangaDTO?.let { mapToSearchComicModel(it) } ?: emptyList()
    }
}