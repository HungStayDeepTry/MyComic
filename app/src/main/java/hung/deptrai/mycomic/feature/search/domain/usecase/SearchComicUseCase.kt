//package hung.deptrai.mycomic.feature.search.domain.usecase
//
//import hung.deptrai.mycomic.core.common.ResultWrapper
//import hung.deptrai.mycomic.core.domain.model.MangaEntity
//import hung.deptrai.mycomic.feature.search.domain.repository.SearchComicRepository
//import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
//import javax.inject.Inject
//
//class SearchComicUseCase @Inject constructor(
//    private val searchComicRepository: SearchComicRepository
//) {
//    private fun mapToSearchComicModel(mangaEntities: List<MangaEntity>): List<SearchComic>? {
//        val searchComics = mangaEntities.map { entity ->
//            val title = entity.title.en ?: "No title"
//            val description = entity.description?.en ?: "No description"
//
//            val imageUrl = entity.coverArt?.attributes?.fileName?.let { fileName ->
//                // Dựa theo format ảnh của MangaDex
//                "https://uploads.mangadex.org/covers/${entity.id}/$fileName.256.jpg"
//            } ?: ""
//
//            val tags: List<String> = (entity.genres.map { it.id } + entity.themes.map { it.id } + entity.contents.map { it.id } + entity.formats.map { it.id }).distinct()
//
//            SearchComic(
//                id = entity.id,
//                title = title,
//                description = description,
//                imageUrl = imageUrl,
//                status = entity.status ?: "",
//                authors = entity.authors.map { it.attributes.name ?: "" },
//                coverArtUrl = imageUrl,
//                tags = tags,
//                contentRating = entity.contentRating,
//                rating = entity.averageRating,
//                views = entity.follows,
//                chapters = entity.lastChapter?.toIntOrNull(),
//                follows = entity.follows,
//                averageRating = entity.averageRating,
//                bayesianRating = entity.bayesianRating,
//                commentsCount = entity.commentsCount
//            )
//        }
//        // Trả về danh sách dưới dạng một biến duy nhất
//        return searchComics
//    }
//
//    suspend fun searchComicByTitle(title: String): ResultWrapper<List<SearchComic>> {
//        // Lấy dữ liệu từ repository
//        return when (val resultWrapper = searchComicRepository.searchComicByTitle(title)) {
//            is ResultWrapper.Success -> {
//                // Chuyển đổi từ MangaEntity sang SearchComicModel
//                val searchComics = mapToSearchComicModel(resultWrapper.data) ?: emptyList()
////                ResultWrapper.Success(searchComics.ifEmpty { emptyList() }) // Đảm bảo trả về danh sách không phải null
//                ResultWrapper.Success(searchComics)
//            }
//            is ResultWrapper.GenericError -> {
//                // Trả về lỗi chi tiết cho UI
//                ResultWrapper.GenericError(
//                    code = resultWrapper.code,
//                    error = "Error: ${resultWrapper.error}"
//                )
//            }
//            is ResultWrapper.NetworkError -> {
//                // Lỗi mạng, có thể cung cấp thêm thông báo lỗi chi tiết
//                ResultWrapper.NetworkError(
//                    exception = resultWrapper.exception
//                )
//            }
//        }
//    }
//}