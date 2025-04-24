//package hung.deptrai.mycomic.feature.search.data.mapper
//
//import hung.deptrai.mycomic.core.domain.model.MangaEntity
//import hung.deptrai.mycomic.core.network.author.SearchAuthorAPI
//import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
//import kotlinx.coroutines.async
//import kotlinx.coroutines.coroutineScope
//import javax.inject.Inject
//
//class MangaDetailsFetcher @Inject constructor(
//    private val authorApi: SearchAuthorAPI,
//    private val coverArtApi: CoverArtAPI
//) {
//
//    suspend fun enrichWithDetails(mangaEntities: List<MangaEntity>): List<MangaEntity> = coroutineScope {
//        val coverArtIds = mangaEntities.mapNotNull { it.coverArt?.id }.distinct()
//        val authorIds = mangaEntities.flatMap { it.authors.map { it.id } }.distinct()
//
//        // Async fetch
//        val authorsDeferred = async { authorApi.getAuthorById(authorIds) }
//        val coverArtDeferreds = coverArtIds.associateWith { id ->
//            async { coverArtApi.getCoverArtById(id) }
//        }
//
//        // Kiểm tra và lấy giá trị từ Response
//        val authorsResponse = authorsDeferred.await()
//        val coverArts = coverArtDeferreds.mapValues {
//            it.value.await()?.body() // Đảm bảo trả về giá trị CoverArtDTO từ Response nếu không null
//        }
//
//        // Kiểm tra và lấy dữ liệu từ Response
//        val authors = authorsResponse.body() ?: emptyList() // Nếu response.body() null thì trả về danh sách rỗng
//
//        // Gán lại dữ liệu chi tiết
//        mangaEntities.map { manga ->
//            val cover = manga.coverArt?.id?.let { coverArts[it] }
//            val mappedAuthors = manga.authors.filter { a -> authors.any { it.id == a.id } } // Lọc các tác giả từ dữ liệu đã nhận
//
//            manga.copy(
//                coverArt = cover,
//                authors = mappedAuthors
//            )
//        }
//    }
//}
