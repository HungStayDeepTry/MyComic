//package hung.deptrai.mycomic.feature.search.domain.usecase
//
//import hung.deptrai.mycomic.core.common.ResultWrapper
//import hung.deptrai.mycomic.feature.search.domain.repository.SearchAuthorRepository
//import hung.deptrai.mycomic.feature.search.domain.model.AuthorSearch
//import javax.inject.Inject
//
//class SearchAuthorUseCase @Inject constructor(
//    private val searchAuthorRepository: SearchAuthorRepository
//) {
//    suspend fun searchAuthorByTitle(title: String): ResultWrapper<List<AuthorSearch>>{
////        return when(val resultWrapper = searchAuthorRepository.searchAuthorByTitle(title)){
////            is ResultWrapper.Success -> {
////                val searchAuthor = mapToAuthorSearch(resultWrapper.data)
////                ResultWrapper.Success(searchAuthor)
////            }
////            is ResultWrapper.GenericError -> {
////                ResultWrapper.GenericError(
////                    code = resultWrapper.code,
////                    error = "Error: ${resultWrapper.error}"
////                )
////            }
////            is ResultWrapper.NetworkError -> {
////                // Lỗi mạng, có thể cung cấp thêm thông báo lỗi chi tiết
////                ResultWrapper.NetworkError(
////                    exception = resultWrapper.exception
////                )
////            }
////        }
//    }
////    private fun mapToAuthorSearch(authorEntity: List<AuthorEntity>): List<AuthorSearch> {
////        return authorEntity.map {  entity ->
////            val mangaCount = entity.relationships
////                .count{
////                    it.type == "manga"
////                }
////            AuthorSearch(
////                id = entity.id,
////                name = entity.name,
////                mangaCount = mangaCount
////            )
////        }
////    }
//}